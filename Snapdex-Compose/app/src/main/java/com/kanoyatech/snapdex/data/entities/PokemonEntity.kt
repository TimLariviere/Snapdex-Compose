package com.kanoyatech.snapdex.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val weight: Double,
    val height: Double,
    val categoryId: Int,
    val abilityId: Int,
    val maleToFemaleRatio: Double
)

@Entity("PokemonTranslations")
data class PokemonTranslationEntity(
    @PrimaryKey val pokemonTranslationId: Int,
    val pokemonId: Int,
    val language: String,
    val name: String,
    val description: String
)

@Entity("PokemonTypes")
data class PokemonTypeEntity(
    @PrimaryKey val pokemonTypeId: Int,
    val pokemonId: Int,
    val type: Int
)

@Entity("PokemonWeaknesses")
data class PokemonWeaknessEntity(
    @PrimaryKey val pokemonWeaknessId: Int,
    val pokemonId: Int,
    val type: Int
)