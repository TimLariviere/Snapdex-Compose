package com.kanoyatech.snapdex.data.datasources.local

import com.kanoyatech.snapdex.data.datasources.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.datasources.local.mappers.toPokemon
import com.kanoyatech.snapdex.domain.datasources.LocalPokemonDataSource
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId

class RoomLocalPokemonDataSource(private val pokemonDao: PokemonDao) : LocalPokemonDataSource {
    override suspend fun getById(pokemonId: PokemonId): Pokemon? {
        return pokemonDao.getBy(pokemonId)?.toPokemon()
    }
}
