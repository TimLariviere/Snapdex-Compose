package com.kanoyatech.snapdex.data.datasources.remote.entities

data class UserRemoteEntity(
    val id: String,
    val avatarId: Int,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
)
