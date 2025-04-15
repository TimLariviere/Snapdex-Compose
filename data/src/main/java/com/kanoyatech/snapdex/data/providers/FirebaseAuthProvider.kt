package com.kanoyatech.snapdex.data.providers

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.providers.AuthProvider
import com.kanoyatech.snapdex.domain.providers.AuthSendPasswordResetEmailError
import com.kanoyatech.snapdex.domain.providers.CreateUserError
import com.kanoyatech.snapdex.domain.providers.ReauthenticateError
import com.kanoyatech.snapdex.domain.providers.SignInError
import com.kanoyatech.snapdex.domain.providers.UpdatePasswordError
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthProvider(private val auth: FirebaseAuth) : AuthProvider {
    override suspend fun getCurrentUserId(): UserId? {
        return auth.currentUser?.uid
    }

    override fun getCurrentUserIdAsFlow(): Flow<UserId?> {
        return callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { trySend(auth.currentUser?.uid) }

            auth.addAuthStateListener(listener)

            awaitClose { auth.removeAuthStateListener(listener) }
        }
    }

    override suspend fun createUser(
        email: String,
        password: String,
    ): TypedResult<UserId?, CreateUserError> {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val value = authResult.user?.uid
            return TypedResult.Success(value)
        } catch (ex: Throwable) {
            val error =
                when (ex) {
                    is FirebaseNetworkException -> CreateUserError.NetworkError
                    is FirebaseAuthWeakPasswordException -> CreateUserError.InvalidPasswordError
                    is FirebaseAuthInvalidCredentialsException -> CreateUserError.InvalidEmailError
                    is FirebaseAuthUserCollisionException -> CreateUserError.EmailAlreadyUsedError
                    else -> CreateUserError.Failure(ex)
                }
            return TypedResult.Error(error)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): TypedResult<UserId?, SignInError> {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val value = authResult.user?.uid
            return TypedResult.Success(value)
        } catch (ex: Throwable) {
            val error =
                when (ex) {
                    is FirebaseNetworkException -> SignInError.NetworkError
                    is FirebaseAuthInvalidUserException -> SignInError.InvalidCredentialsError
                    else -> SignInError.Failure(ex)
                }
            return TypedResult.Error(error)
        }
    }

    override suspend fun sendPasswordResetEmail(
        email: String
    ): TypedResult<Unit, AuthSendPasswordResetEmailError> {
        try {
            auth.sendPasswordResetEmail(email).await()
            return TypedResult.Success(Unit)
        } catch (ex: Throwable) {
            val error =
                when (ex) {
                    is FirebaseNetworkException -> AuthSendPasswordResetEmailError.NetworkError
                    is FirebaseAuthInvalidUserException ->
                        AuthSendPasswordResetEmailError.NoSuchEmailError
                    is FirebaseAuthInvalidCredentialsException ->
                        AuthSendPasswordResetEmailError.InvalidEmailError
                    else -> AuthSendPasswordResetEmailError.Failure(ex)
                }
            return TypedResult.Error(error)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun deleteCurrentUser() {
        auth.currentUser?.let { user ->
            auth.signOut()
            user.delete().await()
        }
    }

    override suspend fun reauthenticate(
        email: String,
        password: String,
    ): TypedResult<Unit, ReauthenticateError> {
        try {
            auth.currentUser?.let { user ->
                val credentials = EmailAuthProvider.getCredential(email, password)
                user.reauthenticate(credentials).await()
            }
            return TypedResult.Success(Unit)
        } catch (ex: Throwable) {
            val error =
                when (ex) {
                    is FirebaseNetworkException -> ReauthenticateError.NetworkError
                    is FirebaseAuthInvalidCredentialsException ->
                        ReauthenticateError.InvalidPasswordError
                    is FirebaseAuthInvalidUserException -> ReauthenticateError.InvalidEmailError
                    else -> ReauthenticateError.Failure(ex)
                }
            return TypedResult.Error(error)
        }
    }

    override suspend fun updatePasswordForCurrentUser(
        newPassword: String
    ): TypedResult<Unit, UpdatePasswordError> {
        try {
            auth.currentUser?.updatePassword(newPassword)?.await()
            return TypedResult.Success(Unit)
        } catch (ex: Throwable) {
            val error =
                when (ex) {
                    is FirebaseNetworkException -> UpdatePasswordError.NetworkError
                    is FirebaseAuthWeakPasswordException -> UpdatePasswordError.InvalidPasswordError
                    else -> UpdatePasswordError.Failure(ex)
                }
            return TypedResult.Error(error)
        }
    }
}
