package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.PokemonId
import java.util.Locale

interface EvolutionChainRepository {
    suspend fun getEvolutionChainForPokemon(pokemonId: PokemonId, locale: Locale): EvolutionChain?
}