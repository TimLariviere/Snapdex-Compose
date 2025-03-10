package com.kanoyatech.snapdex.data

import com.kanoyatech.snapdex.data.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.dao.PokemonDao
import com.kanoyatech.snapdex.data.mappers.toEvolutionChain
import com.kanoyatech.snapdex.data.mappers.toPokemon
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.domain.EvolutionChain
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId
import java.util.Locale

class RoomDataSource(
    private val pokemonDao: PokemonDao,
    private val evolutionChainDao: EvolutionChainDao
): DataSource {
    override suspend fun getAllPokemons(locale: Locale): List<Pokemon> {
        return pokemonDao.getAll().map {
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