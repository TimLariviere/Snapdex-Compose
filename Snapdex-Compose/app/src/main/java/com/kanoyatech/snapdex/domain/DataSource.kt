package com.kanoyatech.snapdex.domain

import java.util.Locale

interface DataSource {
    suspend fun getAllPokemons(locale: Locale): List<Pokemon>
    suspend fun getPokemonBy(id: Int, locale: Locale): Pokemon?
    suspend fun getEvolutionChainFor(pokemonId: PokemonId, locale: Locale): EvolutionChain?
}