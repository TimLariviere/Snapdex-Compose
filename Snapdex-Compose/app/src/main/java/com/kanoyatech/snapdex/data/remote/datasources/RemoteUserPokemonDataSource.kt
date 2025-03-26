package com.kanoyatech.snapdex.data.remote.datasources

import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.kanoyatech.snapdex.data.remote.entities.UserPokemonRemoteEntity
import kotlinx.coroutines.tasks.await

class RemoteUserPokemonDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun getAllForUser(userId: String): List<UserPokemonRemoteEntity> {
        val query =
            firestore.collection("user_pokemons")
                .whereEqualTo("userId", userId)
                .get()
                .await()

        return query.map { document ->
            UserPokemonRemoteEntity(
                userId = document.data["userId"] as String,
                pokemonId = (document.data["pokemonId"] as Long).toInt(),
                createdAt = document.data["createdAt"] as Long
            )
        }
    }

    suspend fun insert(userPokemonRemoteEntity: UserPokemonRemoteEntity) {
        val data = mapOf(
            "userId" to userPokemonRemoteEntity.userId,
            "pokemonId" to userPokemonRemoteEntity.pokemonId,
            "createdAt" to userPokemonRemoteEntity.createdAt
        )

        firestore.collection("user_pokemons")
            .document()
            .set(data)
            .await()
    }

    suspend fun exists(userId: String, pokemonId: Int): Boolean {
        val snapshot =
            firestore.collection("user_pokemons")
                .whereEqualTo("userId", userId)
                .whereEqualTo("pokemonId", pokemonId)
                .count()
                .get(AggregateSource.SERVER)
                .await()

        return snapshot.count > 0
    }

    suspend fun deleteAllForUser(userId: String) {
        val snapshot =
            firestore.collection("user_pokemons")
                .whereEqualTo("userId", userId)
                .get()
                .await()

        snapshot.documents.forEach {
            it.reference.delete()
        }
    }
}