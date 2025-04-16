package com.kanoyatech.snapdex.data.datasources.local.mappers

import com.kanoyatech.snapdex.data.datasources.local.entities.UserEntity
import com.kanoyatech.snapdex.domain.Synced
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
