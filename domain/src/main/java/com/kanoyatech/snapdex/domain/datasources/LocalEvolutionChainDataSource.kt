package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.PokemonId

interface LocalEvolutionChainDataSource {
    suspend fun getForPokemon(pokemonId: PokemonId): EvolutionChain?
}
