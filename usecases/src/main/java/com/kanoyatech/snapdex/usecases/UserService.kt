package com.kanoyatech.snapdex.usecases

import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.datasources.LocalUserDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserError
import com.kanoyatech.snapdex.domain.onError
import com.kanoyatech.snapdex.domain.providers.AnalyticsTracker
import com.kanoyatech.snapdex.domain.providers.AuthProvider
import com.kanoyatech.snapdex.domain.providers.CrashReporter
import com.kanoyatech.snapdex.domain.providers.ReauthenticateError
import com.kanoyatech.snapdex.domain.providers.UpdatePasswordError

sealed interface ChangeNameError {
    object ChangeFailed : ChangeNameError
}

sealed interface ChangePasswordError {
    object InvalidOldPassword : ChangePasswordError

    object InvalidNewPassword : ChangePasswordError

    object UpdatePasswordFailed : ChangePasswordError
}

class UserService(
    private val analyticsTracker: AnalyticsTracker,
    private val crashReporter: CrashReporter,
    private val authProvider: AuthProvider,
    private val localUsers: LocalUserDataSource,
    private val remoteUsers: RemoteUserDataSource,
) {
    suspend fun changeName(newName: String): TypedResult<Unit, ChangeNameError> {
        val userId = authProvider.getCurrentUserId()
        if (userId == null) {
            analyticsTracker.logEvent("no_current_user")
            return TypedResult.Error(ChangeNameError.ChangeFailed)
        }

        val user = localUsers.getById(userId)
        if (user == null) {
            analyticsTracker.logEvent("no_local_user")
            return TypedResult.Error(ChangeNameError.ChangeFailed)
        }

        val updatedUser = user.copy(value = user.value.copy(name = newName))
        localUsers.upsert(updatedUser)

        Retry.execute(
                body = { remoteUsers.upsert(updatedUser) },
                mapFailure = { UpsertRemoteUserError.Failure(it) },
                retryIf = { it == UpsertRemoteUserError.NetworkError },
            )
            .onError { error ->
                when (error) {
                    UpsertRemoteUserError.NetworkError -> Unit
                    is UpsertRemoteUserError.Failure ->
                        crashReporter.recordException(error.exception)
                }
            }

        return TypedResult.Success(Unit)
    }

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
    ): TypedResult<Unit, ChangePasswordError> {
        val userId = authProvider.getCurrentUserId()
        if (userId == null) {
            analyticsTracker.logEvent("no_current_user")
            return TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
        }

        val user =
            localUsers.getById(userId)?.value
                ?: return TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)

        Retry.execute(
                body = { authProvider.reauthenticate(user.email, oldPassword) },
                mapFailure = { ReauthenticateError.Failure(it) },
                retryIf = { it == ReauthenticateError.NetworkError },
            )
            .onError { error ->
                return when (error) {
                    ReauthenticateError.InvalidPasswordError ->
                        TypedResult.Error(ChangePasswordError.InvalidOldPassword)
                    ReauthenticateError.InvalidEmailError ->
                        TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                    ReauthenticateError.NetworkError ->
                        TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                    is ReauthenticateError.Failure -> {
                        crashReporter.recordException(error.exception)
                        TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                    }
                }
            }

        Retry.execute(
                body = { authProvider.updatePasswordForCurrentUser(newPassword) },
                mapFailure = { UpdatePasswordError.Failure(it) },
                retryIf = { it == UpdatePasswordError.NetworkError },
            )
            .onError { error ->
                return when (error) {
                    UpdatePasswordError.InvalidPasswordError ->
                        TypedResult.Error(ChangePasswordError.InvalidNewPassword)
                    UpdatePasswordError.NetworkError ->
                        TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                    is UpdatePasswordError.Failure -> {
                        crashReporter.recordException(error.exception)
                        TypedResult.Error(ChangePasswordError.UpdatePasswordFailed)
                    }
                }
            }

        return TypedResult.Success(Unit)
    }
}
