package com.kanoyatech.snapdex.data.datasources.remote.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.kanoyatech.snapdex.data.datasources.remote.entities.UserRemoteEntity
import kotlinx.coroutines.tasks.await

class RemoteUserDao(private val firestore: FirebaseFirestore) {
    suspend fun upsert(user: UserRemoteEntity): Boolean {
        val data =
            mapOf(
                "avatarId" to user.avatarId,
                "name" to user.name,
                "createdAt" to user.createdAt,
                "updatedAt" to user.updatedAt,
            )

        firestore.collection("users").document(user.id).set(data).await()

        return true
    }

    suspend fun get(id: String): UserRemoteEntity? {
        val document = firestore.collection("users").document(id).get().await()

        val data = document.data ?: return null

        return UserRemoteEntity(
            id = id,
            avatarId = (data["avatarId"] as Long).toInt(),
            name = data["name"] as String,
            createdAt = data["createdAt"] as Long,
            updatedAt = data["updatedAt"] as Long,
        )
    }

    suspend fun delete(id: String) {
        val snapshot = firestore.collection("users").document(id).get().await()

        snapshot.reference.delete().await()
    }
}
