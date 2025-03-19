package com.kanoyatech.snapdex.data.repositories

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.kanoyatech.snapdex.data.local.dao.UserDao
import com.kanoyatech.snapdex.data.local.entities.UserEntity
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserRemoteEntity
import com.kanoyatech.snapdex.domain.models.AvatarId
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.data.utils.Retry
import com.kanoyatech.snapdex.domain.repositories.LoginError
import com.kanoyatech.snapdex.domain.repositories.RegisterError
import com.kanoyatech.snapdex.domain.repositories.SendPasswordResetEmailError
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth: FirebaseAuth,
    private val local: UserDao,
    private val remote: RemoteUserDataSource
): UserRepository {
    override suspend fun register(
        avatarId: AvatarId,
        name: String,
        email: String,
        password: String
    ): TypedResult<Unit, RegisterError> {
        val timestamp = System.currentTimeMillis()

        // Register user in Firebase Auth
        val authResult =
            Retry.execute(
                body = { auth.createUserWithEmailAndPassword(email, password).await() },
                retryIf = { it is FirebaseNetworkException }
            )

        if (authResult.isFailure || authResult.getOrNull()?.user == null) {
            return when (authResult.exceptionOrNull()!!) {
                is FirebaseAuthUserCollisionException -> TypedResult.Error(RegisterError.EmailAlreadyUsed)
                else -> TypedResult.Error(RegisterError.UnknownReason)
            }
        }

        val userId = authResult.getOrNull()!!.user!!.uid

        // Create user in local database
        val userEntity = UserEntity(
            id = userId,
            avatarId = avatarId,
            name = name,
            email = email,
            timestamp = timestamp
        )

        local.upsert(userEntity)

        // Try create user in remote database
        val userRemoteEntity = UserRemoteEntity(
            id = userId,
            avatarId = avatarId,
            name = name,
            timestamp = timestamp
        )

        Retry.execute(
            body = { remote.insert(userRemoteEntity) },
            retryIf = { it is FirebaseNetworkException }
        )

        return TypedResult.Success(Unit)
    }

    override suspend fun login(email: String, password: String): TypedResult<Unit, LoginError> {
        // Log in with Firebase
        val authResult =
            Retry.execute(
                body = { auth.signInWithEmailAndPassword(email, password).await() },
                retryIf = { it is FirebaseNetworkException }
            )

        if (authResult.isFailure || authResult.getOrNull()?.user == null) {
            return when (authResult.exceptionOrNull()!!) {
                else -> TypedResult.Error(LoginError.UnknownReason)
            }
        }

        val userId = authResult.getOrNull()!!.user!!.uid

        // Retrieve user from remote database
        val remoteUserResult =
            Retry.execute(
                body = { remote.get(userId) },
                retryIf = { it is FirebaseNetworkException }
            )

        if (remoteUserResult.isFailure || remoteUserResult.getOrNull() == null) {
            return when (authResult.exceptionOrNull()) {
                null -> TypedResult.Error(LoginError.UserNotFoundInRemote)
                else -> TypedResult.Error(LoginError.UnknownReason)
            }
        }

        val userRemoteEntity = remoteUserResult.getOrNull()!!

        // Add user to local database
        local.upsert(
            UserEntity(
                id = userRemoteEntity.id,
                avatarId = userRemoteEntity.avatarId,
                name = userRemoteEntity.name,
                email = email,
                timestamp = userRemoteEntity.timestamp
            )
        )

        return TypedResult.Success(Unit)
    }

    override suspend fun sendPasswordResetEmail(email: String): TypedResult<Unit, SendPasswordResetEmailError> {
        val result =
            Retry.execute(
                body = { auth.sendPasswordResetEmail(email).await() },
                retryIf = { it is FirebaseNetworkException }
            )

        if (result.isFailure) {
            return TypedResult.Error(SendPasswordResetEmailError.UnknownReason)
        }

        return TypedResult.Success(Unit)
    }
}