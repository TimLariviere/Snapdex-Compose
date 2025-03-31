package com.kanoyatech.snapdex.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "EvolutionChains",
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["startingPokemonId"]
        )
    ]
)
data class EvolutionChainEntity(
    @PrimaryKey val id: Int,
    val startingPokemonId: Int
)

@Entity(
    tableName = "EvolutionChainLinks",
    foreignKeys = [
        ForeignKey(
            entity = EvolutionChainEntity::class,
            parentColumns = ["id"],
            childColumns = ["evolutionChainId"]
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        )
    ]
)
data class EvolutionChainLinkEntity(
    @PrimaryKey val id: Int,
    val evolutionChainId: Int,
    val pokemonId: Int,
    val minLevel: Int
)
