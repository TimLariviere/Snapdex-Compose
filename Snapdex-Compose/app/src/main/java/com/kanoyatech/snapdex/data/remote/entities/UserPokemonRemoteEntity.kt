package com.kanoyatech.snapdex.data.remote.entities

data class UserPokemonRemoteEntity(
    val userId: String,
    val pokemonId: Int,
    val createdAt: Long
)