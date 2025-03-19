package com.kanoyatech.snapdex.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kanoyatech.snapdex.data.RoomDataSource
import com.kanoyatech.snapdex.domain.User
import com.kanoyatech.snapdex.utils.Error
import com.kanoyatech.snapdex.utils.EmptyResult
import com.kanoyatech.snapdex.utils.Result
import kotlinx.coroutines.tasks.await

sealed interface RepositoryError: Error {
    enum class GetCurrentUserError: RepositoryError {
        NOT_LOGGED_IN,
        NOT_FOUND
    }

    enum class AddUserError: RepositoryError {
        FAILED,
        EXCEPTION
    }
}

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataSource: RoomDataSource
) {
    suspend fun getCurrentUser(): Result<User, RepositoryError.GetCurrentUserError> {
        val userId = auth.currentUser?.uid ?: return Result.Error(RepositoryError.GetCurrentUserError.NOT_LOGGED_IN)
        val user = dataSource.getUser(userId)
        return if (user == null) {
            Result.Error(RepositoryError.GetCurrentUserError.NOT_FOUND)
        } else {
            Result.Success(user)
        }
    }

    suspend fun addUser(user: User): EmptyResult<RepositoryError.AddUserError> {
        try {
            // Register user on Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(user.email, user.password)
                .await()

            val userId = authResult.user?.uid ?: return Result.Error(RepositoryError.AddUserError.FAILED)

            val timestamp = System.currentTimeMillis()

            // Insert into database
            dataSource.createUser(
                userId = userId,
                avatarId = user.avatarId,
                name = user.name,
                email = user.email,
                timestamp = timestamp
            )

            // Insert into Firestore
            try {
                val data = mapOf(
                    "avatarId" to user.avatarId,
                    "name" to user.name,
                    "timestamp" to timestamp
                )

                firestore.collection("users")
                    .document(userId)
                    .set(data)
                    .await()
            } catch (e: Exception) {
                Log.d("FIRESTORE", "Failed to insert into Firestore: ${e.message}")
            }

            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(RepositoryError.AddUserError.EXCEPTION)
        }
    }
}