package com.kanoyatech.snapdex.data.datasources.local.mappers

import com.kanoyatech.snapdex.data.datasources.local.entities.UserEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.User

fun UserEntity.toUser(): User {
    return User(id = this.id, avatarId = this.avatarId, name = this.name, email = this.email)
}

fun UserEntity.toSyncedUser(): Synced<User> {
    return Synced<User>(
        value = this.toUser(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun Synced<User>.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.value.id!!,
        avatarId = this.value.avatarId,
        name = this.value.name,
        email = this.value.email,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}

fun User.toUserEntity(timestamp: Long): UserEntity {
    return UserEntity(
        id = this.id!!,
        avatarId = this.avatarId,
        name = this.name,
        email = this.email,
        createdAt = timestamp,
        updatedAt = timestamp,
    )
}

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
