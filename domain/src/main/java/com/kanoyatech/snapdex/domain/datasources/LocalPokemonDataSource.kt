package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId

interface LocalPokemonDataSource {
    suspend fun getById(pokemonId: PokemonId): Pokemon?
}
