package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow

interface LocalUserPokemonDataSource {
    fun observeAllForUser(userId: UserId): Flow<List<Pokemon>>

    suspend fun getAllForUser(userId: String): List<Synced<PokemonId>>

    suspend fun upsert(userId: UserId, pokemon: Synced<PokemonId>)

    suspend fun upsertAll(userId: UserId, pokemons: List<Synced<PokemonId>>)

    suspend fun deleteAllForUser(userId: UserId)

    suspend fun exists(userId: UserId, pokemonId: PokemonId): Boolean
}
