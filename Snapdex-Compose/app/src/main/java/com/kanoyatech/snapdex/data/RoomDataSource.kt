package com.kanoyatech.snapdex.data

import com.kanoyatech.snapdex.data.dao.PokemonDao
import com.kanoyatech.snapdex.data.mappers.toPokemon
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.domain.Pokemon
import java.util.Locale

class RoomDataSource(
    private val pokemonDao: PokemonDao
): DataSource {
    override suspend fun getAll(locale: Locale): List<Pokemon> {
        return pokemonDao.getPokemonsWithRelations(locale.language).map {
            it.toPokemon()
        }
    }
}