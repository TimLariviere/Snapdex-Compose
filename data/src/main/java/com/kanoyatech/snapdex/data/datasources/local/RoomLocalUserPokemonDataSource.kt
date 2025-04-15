package com.kanoyatech.snapdex.data.datasources.local

import com.kanoyatech.snapdex.data.datasources.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.datasources.local.mappers.toPokemon
import com.kanoyatech.snapdex.data.datasources.local.mappers.toSyncedPokemon
import com.kanoyatech.snapdex.data.datasources.local.mappers.toUserPokemonEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.datasources.LocalUserPokemonDataSource
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalUserPokemonDataSource(private val localUserPokemons: UserPokemonDao) :
    LocalUserPokemonDataSource {
    override fun observeAllForUser(userId: UserId): Flow<List<Pokemon>> {
        return localUserPokemons.observeUserPokemons(userId).map { it.map { it.toPokemon() } }
    }

    override suspend fun getAllForUser(userId: String): List<Synced<PokemonId>> {
        return localUserPokemons.getAllForUser(userId).map { it.toSyncedPokemon() }
    }

    override suspend fun upsert(userId: UserId, pokemon: Synced<PokemonId>) {
        val entity = pokemon.toUserPokemonEntity(userId)
        localUserPokemons.upsert(entity)
    }

    override suspend fun upsertAll(userId: UserId, pokemons: List<Synced<PokemonId>>) {
        val entities = pokemons.map { it.toUserPokemonEntity(userId) }
        localUserPokemons.insertAll(entities)
    }

    override suspend fun deleteAllForUser(userId: UserId) {
        localUserPokemons.deleteAllForUser(userId)
    }

    override suspend fun exists(userId: UserId, pokemonId: PokemonId): Boolean {
        return localUserPokemons.exists(userId, pokemonId)
    }
}
