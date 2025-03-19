package com.kanoyatech.snapdex.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kanoyatech.snapdex.data.local.entities.UserEntity
import com.kanoyatech.snapdex.domain.models.UserId

@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(entity: UserEntity)

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getById(id: UserId): UserEntity
}