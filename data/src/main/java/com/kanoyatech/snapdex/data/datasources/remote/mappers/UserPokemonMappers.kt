package com.kanoyatech.snapdex.data.datasources.remote.mappers

import com.kanoyatech.snapdex.data.datasources.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.PokemonId

fun UserPokemonRemoteEntity.toSyncedPokemon(): Synced<PokemonId> {
    return Synced<PokemonId>(
        value = this.pokemonId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun Synced<PokemonId>.toUserRemotePokemonEntity(userId: String): UserPokemonRemoteEntity {
    return UserPokemonRemoteEntity(
        userId = userId,
        pokemonId = this.value,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
