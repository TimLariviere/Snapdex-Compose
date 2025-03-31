package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.flow.Flow

sealed interface CatchPokemonError {
    object CatchFailed: CatchPokemonError
}

interface PokemonRepository {
    fun getPokemonsCaughtByUser(userId: UserId): Flow<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: PokemonId): Pokemon?
    suspend fun catchPokemon(userId: UserId, pokemonId: PokemonId): TypedResult<Unit, CatchPokemonError>
    suspend fun resetForUser(id: UserId)
}