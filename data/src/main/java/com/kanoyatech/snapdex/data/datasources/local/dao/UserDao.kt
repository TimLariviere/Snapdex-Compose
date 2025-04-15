package com.kanoyatech.snapdex.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kanoyatech.snapdex.data.datasources.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert suspend fun upsert(entity: UserEntity)

    @Query("DELETE FROM Users WHERE id = :id") suspend fun delete(id: String)

    @Query("SELECT * FROM Users WHERE id = :id") suspend fun getById(id: String): UserEntity?

    @Query("SELECT * FROM Users WHERE id = :id") fun observeById(id: String): Flow<UserEntity>
}
