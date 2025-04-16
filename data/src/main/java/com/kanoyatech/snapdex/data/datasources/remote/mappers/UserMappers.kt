package com.kanoyatech.snapdex.data.datasources.remote.mappers

import com.kanoyatech.snapdex.data.datasources.remote.entities.UserRemoteEntity
import com.kanoyatech.snapdex.domain.Synced
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
