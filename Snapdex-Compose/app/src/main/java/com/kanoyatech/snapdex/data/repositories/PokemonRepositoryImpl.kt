package com.kanoyatech.snapdex.data.repositories

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.kanoyatech.snapdex.data.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.data.local.mappers.toPokemon
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.data.utils.Retry
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.repositories.CatchPokemonError
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class PokemonRepositoryImpl(
    private val localUserPokemons: UserPokemonDao,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
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

    override suspend fun catchPokemon(userId: UserId, pokemonId: PokemonId): TypedResult<Unit, CatchPokemonError> {
        localUserPokemons.insert(
            UserPokemonEntity(
                userId = userId,
                pokemonId = pokemonId
            )
        )

        val userPokemonRemoteEntity = UserPokemonRemoteEntity(
            userId = userId,
            pokemonId = pokemonId
        )

        val result = Retry.execute(
            body = { remoteUserPokemons.insert(userPokemonRemoteEntity) },
            retryIf = { it is FirebaseNetworkException }
        )

        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            Log.d("Firestore", "Error while inserting UserPokemon: $e")
        }

        return TypedResult.Success(Unit)
    }
}