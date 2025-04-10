package com.kanoyatech.snapdex.data.remote.entities

data class UserPokemonRemoteEntity(
    val id: String?,
    val userId: String,
    val pokemonId: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
