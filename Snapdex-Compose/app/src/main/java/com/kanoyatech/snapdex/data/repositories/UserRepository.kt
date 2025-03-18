package com.kanoyatech.snapdex.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kanoyatech.snapdex.data.RoomDataSource
import com.kanoyatech.snapdex.domain.User
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val dataSource: RoomDataSource
) {
    suspend fun getCurrentUser(): Result<User> {
        val userId = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))
        val user = dataSource.getUser(userId)
        return if (user == null) {
            Result.failure(Exception("No such user '${userId}'"))
        } else {
            Result.success(user)
        }
    }

    suspend fun addUser(user: User): Result<Unit> {
        try {
            // Register user on Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(user.email, user.password)
                .await()

            val userId = authResult.user?.uid ?: return Result.failure(Exception("User registration failed"))

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

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}