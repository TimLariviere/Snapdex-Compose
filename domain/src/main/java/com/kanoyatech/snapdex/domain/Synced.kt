package com.kanoyatech.snapdex.domain

data class Synced<T>(val value: T, val createdAt: Long, val updatedAt: Long)
