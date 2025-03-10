package com.kanoyatech.snapdex.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.kanoyatech.snapdex.data.entities.EvolutionChainLinkEntity
import com.kanoyatech.snapdex.data.entities.PokemonEntity
import com.kanoyatech.snapdex.domain.PokemonId

@Dao
interface EvolutionChainDao {
    @Transaction
    @Query("""
        SELECT ec.* 
        FROM EvolutionChains ec
        LEFT OUTER JOIN EvolutionChainLinks ecl ON ecl.evolutionChainId = ec.id
        WHERE ec.startingPokemonId = :pokemonId OR ecl.pokemonId = :pokemonId
    """)
    suspend fun getFor(pokemonId: PokemonId): EvolutionChainWithRelations?
}

class EvolutionChainWithRelations {
    var id: Int = 0
    var startingPokemonId: Int = 0

    @Relation(
        parentColumn = "startingPokemonId",
        entityColumn = "id",
        entity = PokemonEntity::class
    )
    lateinit var startingPokemon: PokemonWithRelations

    @Relation(
        parentColumn = "id",
        entityColumn = "evolutionChainId",
        entity = EvolutionChainLinkEntity::class
    )
    lateinit var evolvesTo: List<EvolutionChainLinkWithRelations>
}

class EvolutionChainLinkWithRelations {
    var pokemonId: Int = 0

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "id",
        entity = PokemonEntity::class
    )
    lateinit var pokemon: PokemonWithRelations
    var minLevel: Int = 0
}