package com.kanoyatech.snapdex.data.remote.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.kanoyatech.snapdex.data.remote.entities.UserRemoteEntity
import kotlinx.coroutines.tasks.await

class RemoteUserDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun insert(user: UserRemoteEntity): Boolean {
        val data = mapOf(
            "avatarId" to user.avatarId,
            "name" to user.name,
            "timestamp" to user.timestamp
        )

        firestore.collection("users")
            .document(user.id)
            .set(data)
            .await()

        return true
    }

    suspend fun get(id: String): UserRemoteEntity? {
        val document =
            firestore.collection("users")
                .document(id)
                .get()
                .await()

        val data = document.data
            ?: return null

        return UserRemoteEntity(
            id = id,
            avatarId = (data["avatarId"] as Long).toInt(),
            name = data["name"] as String,
            timestamp = data["timestamp"] as Long
        )
    }

    suspend fun delete(id: String) {
        val snapshot =
            firestore.collection("users")
                .document(id)
                .get()
                .await()

        snapshot.reference.delete().await()
    }
}