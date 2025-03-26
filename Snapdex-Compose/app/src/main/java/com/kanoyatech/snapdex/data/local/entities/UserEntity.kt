package com.kanoyatech.snapdex.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class SyncStatus {
    PENDING, SYNCED, FAILED
}

@Entity("Users")
data class UserEntity(
    @PrimaryKey val id: String,
    val avatarId: Int,
    val name: String,
    val email: String,
    val createdAt: Long,
    val syncStatus: SyncStatus
)

@Entity(
    tableName = "UserPokemons",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        ),
    ]
)
data class UserPokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val pokemonId: Int,
    val createdAt: Long,
    val syncStatus: SyncStatus
)