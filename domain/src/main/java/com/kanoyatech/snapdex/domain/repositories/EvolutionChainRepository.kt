package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.PokemonId

interface EvolutionChainRepository {
    suspend fun getEvolutionChainForPokemon(pokemonId: PokemonId): EvolutionChain?
}
