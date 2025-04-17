package com.kanoyatech.snapdex.usecases

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.datasources.DeleteAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.DeleteRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.GetAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.GetRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.LocalUserDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserError
import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.onError
import com.kanoyatech.snapdex.domain.providers.AnalyticsTracker
import com.kanoyatech.snapdex.domain.providers.AuthProvider
import com.kanoyatech.snapdex.domain.providers.AuthSendPasswordResetEmailError
import com.kanoyatech.snapdex.domain.providers.CrashReporter
import com.kanoyatech.snapdex.domain.providers.CreateUserError
import com.kanoyatech.snapdex.domain.providers.SignInError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

sealed interface RegisterError {
    object AccountCreationFailed : RegisterError

    object InvalidPassword : RegisterError

    object InvalidEmail : RegisterError

    object EmailAlreadyUsed : RegisterError
}

sealed interface LoginError {
    object LoginFailed : LoginError

    object InvalidCredentials : LoginError
}

sealed interface SendPasswordResetEmailError {
    object NoSuchEmail : SendPasswordResetEmailError

    object InvalidEmail : SendPasswordResetEmailError

    object SendFailed : SendPasswordResetEmailError
}

sealed interface DeleteCurrentUserError {
    object DeleteFailed : DeleteCurrentUserError
}

class AuthService(
    private val analyticsTracker: AnalyticsTracker,
    private val crashReporter: CrashReporter,
    private val authProvider: AuthProvider,
    private val localUsers: LocalUserDataSource,
    private val localUserPokemons: LocalUserPokemonDataSource,
    private val remoteUsers: RemoteUserDataSource,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
) {
    suspend fun isLoggedIn(): Boolean {
        return authProvider.getCurrentUserId() != null
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentUserAsFlow(): Flow<User?> {
        return authProvider.getCurrentUserIdAsFlow().flatMapLatest { userId ->
            if (userId == null) {
                flowOf(null)
            } else {
                localUsers.observeById(userId).map { it.value }
            }
        }
    }

    suspend fun register(
        avatarId: AvatarId,
        name: String,
        email: String,
        password: String,
    ): TypedResult<Unit, RegisterError> {
        val timestamp = System.currentTimeMillis()

        val userId =
            Retry.execute(
                    body = { authProvider.createUser(email, password) },
                    mapFailure = { CreateUserError.Failure(it) },
                    retryIf = { it == CreateUserError.NetworkError },
                )
                .onError { error ->
                    return when (error) {
                        CreateUserError.InvalidPasswordError ->
                            TypedResult.Error(RegisterError.InvalidPassword)
                        CreateUserError.InvalidEmailError ->
                            TypedResult.Error(RegisterError.InvalidEmail)
                        CreateUserError.EmailAlreadyUsedError ->
                            TypedResult.Error(RegisterError.EmailAlreadyUsed)
                        CreateUserError.NetworkError ->
                            TypedResult.Error(RegisterError.AccountCreationFailed)
                        is CreateUserError.Failure -> {
                            crashReporter.recordException(error.exception, mapOf("email" to email))
                            TypedResult.Error(RegisterError.AccountCreationFailed)
                        }
                    }
                } ?: return TypedResult.Error(RegisterError.AccountCreationFailed)

        analyticsTracker.setUserId(userId)

        val user =
            Synced<User>(
                value = User(id = userId, avatarId = avatarId, name = name, email = email),
                createdAt = timestamp,
                updatedAt = timestamp,
            )

        localUsers.upsert(user)

        Retry.execute(
                body = { remoteUsers.upsert(user) },
                mapFailure = { UpsertRemoteUserError.Failure(it) },
                retryIf = { it == UpsertRemoteUserError.NetworkError },
            )
            .onError { error ->
                when (error) {
                    UpsertRemoteUserError.NetworkError -> Unit
                    is UpsertRemoteUserError.Failure ->
                        crashReporter.recordException(error.exception, mapOf("email" to email))
                }
            }

        return TypedResult.Success(Unit)
    }

    suspend fun login(email: String, password: String): TypedResult<Unit, LoginError> {
        val userId =
            Retry.execute(
                    body = { authProvider.signIn(email, password) },
                    mapFailure = { SignInError.Failure(it) },
                    retryIf = { it is SignInError.NetworkError },
                )
                .onError { error ->
                    return when (error) {
                        SignInError.InvalidCredentialsError ->
                            TypedResult.Error(LoginError.InvalidCredentials)
                        SignInError.NetworkError -> TypedResult.Error(LoginError.LoginFailed)
                        is SignInError.Failure -> {
                            crashReporter.recordException(error.exception, mapOf("email" to email))
                            TypedResult.Error(LoginError.LoginFailed)
                        }
                    }
                } ?: return TypedResult.Error(LoginError.LoginFailed)

        analyticsTracker.setUserId(userId)

        val remoteUser =
            Retry.execute(
                    body = { remoteUsers.getById(userId) },
                    mapFailure = { GetRemoteUserError.Failure(it) },
                    retryIf = { it is GetRemoteUserError.NetworkError },
                )
                .onError { error ->
                    if (error is GetRemoteUserError.Failure) {
                        crashReporter.recordException(error.exception, mapOf("userId" to userId))
                    }
                    return TypedResult.Error(LoginError.LoginFailed)
                }

        if (remoteUser == null) {
            analyticsTracker.logEvent("user_not_found_in_remote", mapOf("email" to email))
            return TypedResult.Error(LoginError.LoginFailed)
        }

        // Remote doesn't provide email address, so we inject it here
        val user = remoteUser.copy(value = remoteUser.value.copy(email = email))

        val remoteUserPokemons =
            Retry.execute(
                    body = { remoteUserPokemons.getAllForUser(userId) },
                    mapFailure = { GetAllForRemoteUserError.Failure(it) },
                    retryIf = { it is GetAllForRemoteUserError.NetworkError },
                )
                .onError { error ->
                    when (error) {
                        GetAllForRemoteUserError.NetworkError -> Unit
                        is GetAllForRemoteUserError.Failure -> {
                            crashReporter.recordException(
                                error.exception,
                                mapOf("userId" to userId),
                            )
                        }
                    }
                } ?: emptyList()

        localUsers.upsert(user)
        localUserPokemons.upsertAll(userId, remoteUserPokemons)

        return TypedResult.Success(Unit)
    }

    suspend fun sendPasswordResetEmail(
        email: String
    ): TypedResult<Unit, SendPasswordResetEmailError> {
        Retry.execute(
                body = { authProvider.sendPasswordResetEmail(email) },
                mapFailure = { AuthSendPasswordResetEmailError.Failure(it) },
                retryIf = { it is AuthSendPasswordResetEmailError.NetworkError },
            )
            .onError { error ->
                return when (error) {
                    AuthSendPasswordResetEmailError.NoSuchEmailError ->
                        TypedResult.Error(SendPasswordResetEmailError.NoSuchEmail)
                    AuthSendPasswordResetEmailError.InvalidEmailError ->
                        TypedResult.Error(SendPasswordResetEmailError.InvalidEmail)
                    AuthSendPasswordResetEmailError.NetworkError ->
                        TypedResult.Error(SendPasswordResetEmailError.SendFailed)
                    is AuthSendPasswordResetEmailError.Failure -> {
                        crashReporter.recordException(error.exception, mapOf("email" to email))
                        TypedResult.Error(SendPasswordResetEmailError.SendFailed)
                    }
                }
            }

        return TypedResult.Success(Unit)
    }

    suspend fun logout() {
        authProvider.signOut()
        analyticsTracker.setUserId(null)
    }

    suspend fun deleteCurrentUser(): TypedResult<Unit, DeleteCurrentUserError> {
        val userId = authProvider.getCurrentUserId()
        if (userId == null) {
            analyticsTracker.logEvent("no_current_user")
            return TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
        }

        Retry.execute(
                body = { remoteUserPokemons.deleteAllForUser(userId) },
                mapFailure = { DeleteAllForRemoteUserError.Failure(it) },
                retryIf = { it is DeleteAllForRemoteUserError.NetworkError },
            )
            .onError { error ->
                return when (error) {
                    DeleteAllForRemoteUserError.NetworkError ->
                        TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                    is DeleteAllForRemoteUserError.Failure -> {
                        crashReporter.recordException(error.exception)
                        TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                    }
                }
            }

        Retry.execute(
                body = { remoteUsers.delete(userId) },
                mapFailure = { DeleteRemoteUserError.Failure(it) },
                retryIf = { it is DeleteRemoteUserError.NetworkError },
            )
            .onError { error ->
                return when (error) {
                    DeleteRemoteUserError.NetworkError ->
                        TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                    is DeleteRemoteUserError.Failure -> {
                        crashReporter.recordException(error.exception)
                        TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                    }
                }
            }

        localUserPokemons.deleteAllForUser(userId)
        localUsers.delete(userId)
        authProvider.deleteCurrentUser()

        analyticsTracker.setUserId(null)

        return TypedResult.Success(Unit)
    }
}
