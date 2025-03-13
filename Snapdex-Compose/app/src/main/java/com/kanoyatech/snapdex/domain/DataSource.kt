package com.kanoyatech.snapdex.domain

import java.util.Locale

interface DataSource {
    suspend fun createUser(userId: UserId, name: String)
    suspend fun hasCaughtPokemon(userId: UserId, pokemonId: PokemonId): Boolean
    suspend fun addPokemonToUser(userId: UserId, pokemonId: PokemonId)
    suspend fun getCaughtPokemons(userId: UserId, locale: Locale): List<Pokemon>
    suspend fun getPokemonBy(id: Int, locale: Locale): Pokemon?
    suspend fun getEvolutionChainFor(pokemonId: PokemonId, locale: Locale): EvolutionChain?
}