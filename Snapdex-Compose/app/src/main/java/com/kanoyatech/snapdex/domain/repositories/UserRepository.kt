package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.utils.TypedResult

sealed interface RegisterError {
    data object EmailAlreadyUsed: RegisterError
    data object UnknownReason: RegisterError
}

sealed interface LoginError {
    data object UnknownReason: LoginError
    data object UserNotFoundInRemote : LoginError
}

sealed interface SendPasswordResetEmailError {
    data object UnknownReason: SendPasswordResetEmailError
}

interface UserRepository {
    suspend fun register(avatarId: AvatarId, name: String, email: String, password: String): TypedResult<Unit, RegisterError>
    suspend fun login(email: String, password: String): TypedResult<Unit, LoginError>
    suspend fun sendPasswordResetEmail(email: String): TypedResult<Unit, SendPasswordResetEmailError>
}