package com.kanoyatech.snapdex.data.mappers

import com.kanoyatech.snapdex.data.entities.UserEntity
import com.kanoyatech.snapdex.domain.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        avatarId = avatarId,
        name = name,
        email = email,
        password = ""
    )
}