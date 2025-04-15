package com.kanoyatech.snapdex.data.datasources.local

import com.kanoyatech.snapdex.data.datasources.local.dao.UserDao
import com.kanoyatech.snapdex.data.datasources.local.mappers.toSyncedUser
import com.kanoyatech.snapdex.data.datasources.local.mappers.toUserEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.datasources.LocalUserDataSource
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalUserDataSource(private val localUsers: UserDao) : LocalUserDataSource {
    override fun observeById(userId: UserId): Flow<Synced<User>> {
        return localUsers.observeById(userId).map { it.toSyncedUser() }
    }

    override suspend fun getById(userId: UserId): Synced<User>? {
        return localUsers.getById(userId)?.toSyncedUser()
    }

    override suspend fun upsert(user: Synced<User>) {
        val userEntity = user.toUserEntity()
        localUsers.upsert(userEntity)
    }

    override suspend fun delete(userId: UserId) {
        localUsers.delete(userId)
    }
}
