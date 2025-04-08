package com.kanoyatech.snapdex.data.repositories

import android.os.Bundle
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kanoyatech.snapdex.data.local.dao.UserDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.entities.UserEntity
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserRemoteEntity
import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.data.Retry
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.ChangeNameError
import com.kanoyatech.snapdex.domain.repositories.ChangePasswordError
import com.kanoyatech.snapdex.domain.repositories.DeleteCurrentUserError
import com.kanoyatech.snapdex.domain.repositories.LoginError
import com.kanoyatech.snapdex.domain.repositories.RegisterError
import com.kanoyatech.snapdex.domain.repositories.SendPasswordResetEmailError
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.data.currentUserAsFlow
import com.kanoyatech.snapdex.data.recordExceptionWithKeys
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val analytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics,
    private val localUsers: UserDao,
    private val localUserPokemons: UserPokemonDao,
    private val remoteUsers: RemoteUserDataSource,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
): UserRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCurrentUser(): Flow<User?> {
        return auth.currentUserAsFlow()
            .flatMapLatest { firebaseUser ->
                if (firebaseUser == null) {
                    flowOf(null)
                } else {
                    localUsers.observeById(firebaseUser.uid)
                }
            }
            .map { userEntity: UserEntity? ->
                userEntity?.let {
                    User(
                        id = userEntity.id,
                        avatarId = userEntity.avatarId,
                        name = userEntity.name,
                        email = userEntity.email
                    )
                }
            }
    }

    override suspend fun register(avatarId: AvatarId, name: String, email: String, password: String): TypedResult<Unit, RegisterError> {
        val timestamp = System.currentTimeMillis()

        // Register user in Firebase Auth
        val authResult = Retry.execute(
            body = { auth.createUserWithEmailAndPassword(email, password).await() },
            retryIf = { it is FirebaseNetworkException }
        )

        authResult.exceptionOrNull()?.let { exception ->
            return when (exception) {
                is FirebaseAuthWeakPasswordException ->
                    TypedResult.Error(RegisterError.InvalidPassword)
                is FirebaseAuthInvalidCredentialsException ->
                    TypedResult.Error(RegisterError.InvalidEmail)
                is FirebaseAuthUserCollisionException ->
                    TypedResult.Error(RegisterError.EmailAlreadyUsed)
                is FirebaseNetworkException ->
                    TypedResult.Error(RegisterError.AccountCreationFailed)
                else -> {
                    crashlytics.recordExceptionWithKeys(exception, mapOf("email" to email))
                    TypedResult.Error(RegisterError.AccountCreationFailed)
                }
            }
        }

        val userId = authResult.getOrNull()?.user?.uid
            ?: return TypedResult.Error(RegisterError.AccountCreationFailed)

        analytics.setUserId(userId)

        // Save to local DB
        val userEntity = UserEntity(
            id = userId,
            avatarId = avatarId,
            name = name,
            email = email,
            createdAt = timestamp,
            updatedAt = timestamp
        )

        localUsers.upsert(userEntity)

        // Try syncing to Firestore
        val userRemoteEntity = UserRemoteEntity(
            id = userId,
            avatarId = avatarId,
            name = name,
            createdAt = timestamp,
            updatedAt = timestamp
        )

        val remoteResult = Retry.execute(
            body = { remoteUsers.upsert(userRemoteEntity) },
            retryIf = { it is FirebaseNetworkException }
        )

        remoteResult.exceptionOrNull()?.let { e ->
            if (e !is FirebaseNetworkException) {
                crashlytics.recordExceptionWithKeys(e, mapOf("userId" to userId))
            }
        }

        return TypedResult.Success(Unit)
    }

    override suspend fun login(email: String, password: String): TypedResult<Unit, LoginError> {
        val authResult = Retry.execute(
            body = { auth.signInWithEmailAndPassword(email, password).await() },
            retryIf = { it is FirebaseNetworkException }
        )

        authResult.exceptionOrNull()?.let { e ->
            return when (e) {
                is FirebaseAuthInvalidUserException -> TypedResult.Error(LoginError.InvalidCredentials)
                is FirebaseNetworkException -> TypedResult.Error(LoginError.LoginFailed)
                else -> {
                    crashlytics.recordExceptionWithKeys(e, mapOf("email" to email))
                    TypedResult.Error(LoginError.LoginFailed)
                }
            }
        }

        val userId = authResult.getOrNull()?.user?.uid
            ?: return TypedResult.Error(LoginError.LoginFailed)

        analytics.setUserId(userId)

        val remoteUserResult = Retry.execute(
            body = { remoteUsers.get(userId) },
            retryIf = { it is FirebaseNetworkException }
        )

        val remoteUserPokemonsResult = Retry.execute(
            body = { remoteUserPokemons.getAllForUser(userId) },
            retryIf = { it is FirebaseNetworkException }
        )

        remoteUserResult.exceptionOrNull()?.let { e ->
            if (e !is FirebaseNetworkException) {
                crashlytics.recordExceptionWithKeys(e, mapOf("userId" to userId))
            }
            return TypedResult.Error(LoginError.LoginFailed)
        }

        val remoteUser = remoteUserResult.getOrNull()
        if (remoteUser == null) {
            analytics.logEvent("user_not_found_in_remote", Bundle().apply {
                putString("email", email)
            })
            return TypedResult.Error(LoginError.LoginFailed)
        }

        localUsers.upsert(
            UserEntity(
                id = remoteUser.id,
                avatarId = remoteUser.avatarId,
                name = remoteUser.name,
                email = email,
                createdAt = remoteUser.createdAt,
                updatedAt = remoteUser.updatedAt
            )
        )

        remoteUserPokemonsResult.getOrNull()?.let { remotePokemons ->
            localUserPokemons.insertAll(
                remotePokemons.map {
                    UserPokemonEntity(
                        userId = it.userId,
                        pokemonId = it.pokemonId,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
            )
        }

        return TypedResult.Success(Unit)
    }

    override suspend fun sendPasswordResetEmail(email: String): TypedResult<Unit, SendPasswordResetEmailError> {
        val result = Retry.execute(
            body = { auth.sendPasswordResetEmail(email).await() },
            retryIf = { it is FirebaseNetworkException }
        )

        result.exceptionOrNull()?.let { e ->
            return when (e) {
                is FirebaseAuthInvalidUserException -> TypedResult.Error(SendPasswordResetEmailError.NoSuchEmail)
                is FirebaseAuthInvalidCredentialsException -> TypedResult.Error(SendPasswordResetEmailError.InvalidEmail)
                is FirebaseNetworkException -> TypedResult.Error(SendPasswordResetEmailError.SendFailed)
                else -> {
                    crashlytics.recordExceptionWithKeys(e, mapOf("email" to email))
                    TypedResult.Error(SendPasswordResetEmailError.SendFailed)
                }
            }
        }

        return TypedResult.Success(Unit)
    }

    override suspend fun logout() {
        auth.signOut()
        analytics.setUserId(null)
    }

    override suspend fun deleteCurrentUser(): TypedResult<Unit, DeleteCurrentUserError> {
        val user = auth.currentUser
        if (user == null) {
            analytics.logEvent("no_current_user", Bundle())
            return TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
        }

        val userId = user.uid

        val deleteUserResult = Retry.execute(
            body = { remoteUserPokemons.deleteAllForUser(userId) },
            retryIf = { it is FirebaseNetworkException }
        )

        deleteUserResult.exceptionOrNull()?.let { e ->
            return when (e) {
                is FirebaseNetworkException -> TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                else -> {
                    crashlytics.recordException(e)
                    TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                }
            }
        }

        val deleteUserPokemonsResult = Retry.execute(
            body = { remoteUsers.delete(userId) },
            retryIf = { it is FirebaseNetworkException }
        )

        deleteUserPokemonsResult.exceptionOrNull()?.let { e ->
            return when (e) {
                is FirebaseNetworkException -> TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                else -> {
                    crashlytics.recordException(e)
                    TypedResult.Error(DeleteCurrentUserError.DeleteFailed)
                }
            }
        }

        localUserPokemons.deleteAllForUser(userId)
        localUsers.delete(userId)

        user.delete()

        auth.signOut()
        analytics.setUserId(null)

        return TypedResult.Success(Unit)
    }

    override suspend fun changeName(newName: String): TypedResult<Unit, ChangeNameError> {
        val user = auth.currentUser
        if (user == null) {
            analytics.logEvent("no_current_user", Bundle())
            return TypedResult.Error(ChangeNameError.ChangeFailed)
        }

        val userEntity = localUsers.getById(user.uid)
        if (userEntity == null) {
            analytics.logEvent("no_local_user", Bundle())
            return TypedResult.Error(ChangeNameError.ChangeFailed)
        }

        val updatedEntity = userEntity.copy(name = newName)
        localUsers.upsert(updatedEntity)
        return TypedResult.Success(Unit)
    }

    override suspend fun changePassword(oldPassword:String, newPassword: String): TypedResult<Unit, ChangePasswordError> {
        val user = auth.currentUser
        if (user == null) {
            analytics.logEvent("no_current_user", Bundle())
            return TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
        }

        val authCredential = EmailAuthProvider.getCredential(user.email.toString(), oldPassword)
        val reauthenticationResult = Retry.execute(
            body = { user.reauthenticate(authCredential).await() },
            retryIf = { it is FirebaseNetworkException }
        )

        reauthenticationResult.exceptionOrNull()?.let { e ->
            return when(e) {
                is FirebaseAuthInvalidCredentialsException -> TypedResult.Error(ChangePasswordError.InvalidOldPassword)
                is FirebaseAuthInvalidUserException -> TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                is FirebaseNetworkException -> TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                else -> {
                    crashlytics.recordException(e)
                    TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                }
            }
        }

        val updatePasswordResult = Retry.execute(
            body = { user.updatePassword(newPassword).await() },
            retryIf = { it is FirebaseNetworkException }
        )

        updatePasswordResult.exceptionOrNull()?.let { e ->
            return when(e) {
                is FirebaseAuthWeakPasswordException -> TypedResult.Error(ChangePasswordError.InvalidNewPassword)
                is FirebaseNetworkException -> TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                else -> {
                    crashlytics.recordException(e)
                    TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                }
            }
        }

        return TypedResult.Success(Unit)
    }
}