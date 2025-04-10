package com.kanoyatech.snapdex.domain.models

data class User(val id: UserId?, val avatarId: AvatarId, val name: String, val email: String)
