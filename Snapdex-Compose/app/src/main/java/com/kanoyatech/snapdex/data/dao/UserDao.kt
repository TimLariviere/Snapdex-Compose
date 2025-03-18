package com.kanoyatech.snapdex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kanoyatech.snapdex.data.entities.UserEntity
import com.kanoyatech.snapdex.domain.UserId

@Dao
interface UserDao {
    @Insert
    suspend fun insert(entity: UserEntity)

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getById(id: UserId): UserEntity?
}