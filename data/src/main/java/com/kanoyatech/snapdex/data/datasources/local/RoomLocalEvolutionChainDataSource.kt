package com.kanoyatech.snapdex.data.datasources.local

import com.kanoyatech.snapdex.data.datasources.local.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.datasources.local.mappers.toPokemon
import com.kanoyatech.snapdex.domain.datasources.LocalEvolutionChainDataSource
import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.PokemonId

class RoomLocalEvolutionChainDataSource(private val localEvolutionChain: EvolutionChainDao) :
    LocalEvolutionChainDataSource {
    override suspend fun getForPokemon(pokemonId: PokemonId): EvolutionChain? {
        val evolutionChainWithRelations = localEvolutionChain.getFor(pokemonId) ?: return null

        return EvolutionChain(
            startingPokemon = evolutionChainWithRelations.startingPokemon.toPokemon(),
            evolutions =
                evolutionChainWithRelations.evolvesTo.associate { evolutionChainLink ->
                    evolutionChainLink.minLevel to evolutionChainLink.pokemon.toPokemon()
                },
        )
    }
}
