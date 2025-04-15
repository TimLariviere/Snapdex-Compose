package com.kanoyatech.snapdex.data.datasources.remote.mappers

import com.kanoyatech.snapdex.data.datasources.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.data.datasources.remote.entities.UserRemoteEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.User

fun UserRemoteEntity.toUser(): User {
    return User(id = this.id, avatarId = this.avatarId, name = this.name, email = "")
}

fun UserRemoteEntity.toSyncedUser(): Synced<User> {
    return Synced<User>(
        value = this.toUser(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun Synced<User>.toUserRemoteEntity(): UserRemoteEntity {
    return UserRemoteEntity(
        id = this.value.id!!,
        avatarId = this.value.avatarId,
        name = this.value.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

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
