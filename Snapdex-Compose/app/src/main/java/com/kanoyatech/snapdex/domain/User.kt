package com.kanoyatech.snapdex.domain

data class User(
    val id: UserId?,
    val avatarId: Int,
    val name: String,
    val email: String,
    val password: String
)