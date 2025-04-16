package com.kanoyatech.snapdex.data.datasources.local.mappers

import com.kanoyatech.snapdex.data.datasources.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.PokemonId

fun Synced<PokemonId>.toUserPokemonEntity(userId: String): UserPokemonEntity {
    return UserPokemonEntity(
        userId = userId,
        pokemonId = this.value,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun UserPokemonEntity.toSyncedPokemon(): Synced<PokemonId> {
    return Synced<PokemonId>(
        value = this.pokemonId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
