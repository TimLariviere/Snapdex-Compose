package com.kanoyatech.snapdex.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("Users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String
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
    val pokemonId: Int
)