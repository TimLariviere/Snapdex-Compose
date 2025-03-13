package com.kanoyatech.snapdex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.kanoyatech.snapdex.data.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(entity: UserEntity)
}