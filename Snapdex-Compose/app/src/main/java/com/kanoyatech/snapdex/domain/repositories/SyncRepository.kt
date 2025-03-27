package com.kanoyatech.snapdex.domain.repositories

interface SyncRepository {
    suspend fun syncData()
}