package com.kanoyatech.snapdex.data.remote.entities

data class UserRemoteEntity(
    val id: String,
    val avatarId: Int,
    val name: String,
    val timestamp: Long
)