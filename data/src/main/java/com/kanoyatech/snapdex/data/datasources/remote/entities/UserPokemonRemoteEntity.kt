package com.kanoyatech.snapdex.data.datasources.remote.entities

data class UserPokemonRemoteEntity(
    val id: String? = null,
    val userId: String,
    val pokemonId: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
