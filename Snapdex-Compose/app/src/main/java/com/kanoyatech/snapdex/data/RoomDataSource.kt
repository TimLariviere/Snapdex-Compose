package com.kanoyatech.snapdex.data

import com.kanoyatech.snapdex.data.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.dao.PokemonDao
import com.kanoyatech.snapdex.data.dao.UserDao
import com.kanoyatech.snapdex.data.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.entities.UserEntity
import com.kanoyatech.snapdex.data.entities.UserPokemonEntity
import com.kanoyatech.snapdex.data.mappers.toEvolutionChain
import com.kanoyatech.snapdex.data.mappers.toPokemon
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.domain.EvolutionChain
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.domain.UserId
import java.util.Locale

class RoomDataSource(
    private val pokemonDao: PokemonDao,
    private val evolutionChainDao: EvolutionChainDao,
    private val userDao: UserDao,
    private val userPokemonDao: UserPokemonDao
): DataSource {
    override suspend fun createUser(userId: UserId, name: String) {
        val entity = UserEntity(id = userId, name = name)
        userDao.insert(entity)
    }

    override suspend fun hasCaughtPokemon(userId: UserId, pokemonId: PokemonId): Boolean {
        return userPokemonDao.exists(userId, pokemonId)
    }

    override suspend fun addPokemonToUser(userId: UserId, pokemonId: PokemonId) {
        val entity = UserPokemonEntity(userId = userId, pokemonId = pokemonId)
        userPokemonDao.insert(entity)
    }

    override suspend fun getCaughtPokemons(userId: UserId, locale: Locale): List<Pokemon> {
        return userPokemonDao.getUserPokemons(userId).map {
            it.toPokemon(locale)
        }
    }

    override suspend fun getPokemonBy(id: Int, locale: Locale): Pokemon? {
        return pokemonDao.getBy(id = id)?.toPokemon(locale)
    }

    override suspend fun getEvolutionChainFor(pokemonId: PokemonId, locale: Locale): EvolutionChain? {
        return evolutionChainDao.getFor(pokemonId)?.toEvolutionChain(locale)
    }
}