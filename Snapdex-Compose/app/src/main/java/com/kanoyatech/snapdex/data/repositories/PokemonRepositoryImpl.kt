package com.kanoyatech.snapdex.data.repositories

import com.kanoyatech.snapdex.data.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.mappers.toPokemon
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class PokemonRepositoryImpl(
    private val localUserPokemons: UserPokemonDao,
    private val localPokemons: PokemonDao
): PokemonRepository {


    override fun getPokemonsCaughtByUser(userId: UserId, locale: Locale): Flow<List<Pokemon>> {
        return localUserPokemons.observeUserPokemons(userId)
            .map { userPokemons ->
                userPokemons.map { it.toPokemon(locale) }
            }
    }

    override suspend fun getPokemonById(pokemonId: PokemonId, locale: Locale): Pokemon? {
        val pokemonWithRelations = localPokemons.getBy(pokemonId)
            ?: return null

        return pokemonWithRelations.toPokemon(locale)
    }
}