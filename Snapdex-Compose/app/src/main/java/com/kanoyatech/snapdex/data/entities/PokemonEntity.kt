package com.kanoyatech.snapdex.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Pokemons",
    foreignKeys = [
        ForeignKey(
            entity = AbilityEntity::class,
            parentColumns = ["id"],
            childColumns = ["abilityId"]
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val weight: Double,
    val height: Double,
    val categoryId: Int,
    val abilityId: Int,
    val maleToFemaleRatio: Double
)

@Entity(
    tableName = "PokemonTranslations",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ]
)
data class PokemonTranslationEntity(
    @PrimaryKey val pokemonTranslationId: Int,
    val pokemonId: Int,
    val language: String,
    val name: String,
    val description: String
)

@Entity(
    tableName = "PokemonTypes",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ]
)
data class PokemonTypeEntity(
    @PrimaryKey val pokemonTypeId: Int,
    val pokemonId: Int,
    val type: Int
)

@Entity(
    tableName = "PokemonWeaknesses",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ]
)
data class PokemonWeaknessEntity(
    @PrimaryKey val pokemonWeaknessId: Int,
    val pokemonId: Int,
    val type: Int
)