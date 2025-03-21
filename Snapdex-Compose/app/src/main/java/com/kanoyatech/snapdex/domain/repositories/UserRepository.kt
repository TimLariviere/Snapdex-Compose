package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.flow.Flow

sealed interface RegisterError {
    data object EmailAlreadyUsed: RegisterError
    data object UnknownReason: RegisterError
}

sealed interface LoginError {
    data object UnknownReason: LoginError
    data object UserNotFoundInRemote : LoginError
    data object InvalidCredentials : LoginError
}

sealed interface SendPasswordResetEmailError {
    data object UnknownReason: SendPasswordResetEmailError
}

sealed interface LogoutError {

}

sealed interface ChangePasswordError {
    object UnknownReason: ChangePasswordError
    object ReauthenticationFailed: ChangePasswordError
}

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun register(avatarId: AvatarId, name: String, email: String, password: String): TypedResult<Unit, RegisterError>
    suspend fun login(email: String, password: String): TypedResult<Unit, LoginError>
    suspend fun sendPasswordResetEmail(email: String): TypedResult<Unit, SendPasswordResetEmailError>
    suspend fun logout(): TypedResult<Unit, LogoutError>
    suspend fun deleteCurrentUser(): TypedResult<Unit, Unit>
    suspend fun changePassword(oldPassword: String, newPassword: String): TypedResult<Unit, ChangePasswordError>
}