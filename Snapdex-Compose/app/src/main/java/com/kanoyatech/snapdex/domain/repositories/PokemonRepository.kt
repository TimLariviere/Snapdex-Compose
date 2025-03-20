package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface PokemonRepository {
    fun getPokemonsCaughtByUser(userId: UserId, locale: Locale): Flow<List<Pokemon>>
    suspend fun getPokemonById(pokemonId: PokemonId, locale: Locale): Pokemon?
}