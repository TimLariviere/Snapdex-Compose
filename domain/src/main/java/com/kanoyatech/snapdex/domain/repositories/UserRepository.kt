package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.domain.models.User
import kotlinx.coroutines.flow.Flow

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

sealed interface ChangeNameError {
    object ChangeFailed : ChangeNameError
}

sealed interface ChangePasswordError {
    object InvalidOldPassword : ChangePasswordError

    object InvalidNewPassword : ChangePasswordError

    object UpdatePasswordFailed : ChangePasswordError
}

interface UserRepository {
    suspend fun isLoggedIn(): Boolean

    fun getCurrentUserFlow(): Flow<User?>

    suspend fun register(
        avatarId: AvatarId,
        name: String,
        email: String,
        password: String,
    ): TypedResult<Unit, RegisterError>

    suspend fun login(email: String, password: String): TypedResult<Unit, LoginError>

    suspend fun sendPasswordResetEmail(
        email: String
    ): TypedResult<Unit, SendPasswordResetEmailError>

    suspend fun logout()

    suspend fun deleteCurrentUser(): TypedResult<Unit, DeleteCurrentUserError>

    suspend fun changeName(newName: String): TypedResult<Unit, ChangeNameError>

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
    ): TypedResult<Unit, ChangePasswordError>
}
