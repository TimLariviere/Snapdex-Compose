package com.kanoyatech.snapdex.domain.providers

import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow

sealed interface CreateUserError {
    data class Failure(val exception: Throwable) : CreateUserError

    object NetworkError : CreateUserError

    object InvalidPasswordError : CreateUserError

    object InvalidEmailError : CreateUserError

    object EmailAlreadyUsedError : CreateUserError
}

sealed interface SignInError {
    data class Failure(val exception: Throwable) : SignInError

    object NetworkError : SignInError

    object InvalidCredentialsError : SignInError
}

sealed interface AuthSendPasswordResetEmailError {
    data class Failure(val exception: Throwable) : AuthSendPasswordResetEmailError

    object NetworkError : AuthSendPasswordResetEmailError

    object NoSuchEmailError : AuthSendPasswordResetEmailError

    object InvalidEmailError : AuthSendPasswordResetEmailError
}

sealed interface ReauthenticateError {
    data class Failure(val exception: Throwable) : ReauthenticateError

    object NetworkError : ReauthenticateError

    object InvalidPasswordError : ReauthenticateError

    object InvalidEmailError : ReauthenticateError
}

sealed interface UpdatePasswordError {
    data class Failure(val exception: Throwable) : UpdatePasswordError

    object NetworkError : UpdatePasswordError

    object InvalidPasswordError : UpdatePasswordError
}

interface AuthProvider {
    suspend fun getCurrentUserId(): UserId?

    fun getCurrentUserIdAsFlow(): Flow<UserId?>

    suspend fun createUser(email: String, password: String): TypedResult<UserId?, CreateUserError>

    suspend fun signIn(email: String, password: String): TypedResult<UserId?, SignInError>

    suspend fun sendPasswordResetEmail(
        email: String
    ): TypedResult<Unit, AuthSendPasswordResetEmailError>

    suspend fun signOut()

    suspend fun deleteCurrentUser() // delete + sign out

    suspend fun reauthenticate(
        email: String,
        password: String,
    ): TypedResult<Unit, ReauthenticateError>

    suspend fun updatePasswordForCurrentUser(
        newPassword: String
    ): TypedResult<Unit, UpdatePasswordError>
}
