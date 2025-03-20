package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.flow.Flow
import java.util.Locale

sealed interface CatchPokemonError {
    data object UnknownReason: CatchPokemonError
}

interface PokemonRepository {
    fun getPokemonsCaughtByUser(userId: UserId, locale: Locale): Flow<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: PokemonId, locale: Locale): Pokemon?
    suspend fun catchPokemon(userId: UserId, pokemonId: PokemonId): TypedResult<Unit, CatchPokemonError>
}