package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun observeById(userId: UserId): Flow<Synced<User>>

    suspend fun getById(userId: UserId): Synced<User>?

    suspend fun upsert(user: Synced<User>)

    suspend fun delete(userId: UserId)
}
