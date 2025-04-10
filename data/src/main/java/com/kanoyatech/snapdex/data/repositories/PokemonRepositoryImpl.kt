package com.kanoyatech.snapdex.data.repositories

import android.os.Bundle
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kanoyatech.snapdex.data.Retry
import com.kanoyatech.snapdex.data.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.data.local.mappers.toPokemon
import com.kanoyatech.snapdex.data.recordExceptionWithKeys
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.repositories.CatchPokemonError
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val analytics: FirebaseAnalytics,
    private val crashlytics: FirebaseCrashlytics,
    private val localUserPokemons: UserPokemonDao,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
    private val localPokemons: PokemonDao,
) : PokemonRepository {
    override fun getPokemonsCaughtByUser(userId: UserId): Flow<List<Pokemon>> {
        return localUserPokemons.observeUserPokemons(userId).map { userPokemons ->
            userPokemons.map { it.toPokemon() }
        }
    }

    override suspend fun getPokemonById(pokemonId: PokemonId): Pokemon? {
        val pokemonWithRelations = localPokemons.getBy(pokemonId) ?: return null

        return pokemonWithRelations.toPokemon()
    }

    override suspend fun catchPokemon(
        userId: UserId,
        pokemonId: PokemonId,
    ): TypedResult<Unit, CatchPokemonError> {
        val timestamp = System.currentTimeMillis()

        // The user has already caught that pokemon, so we don't do anything
        if (localUserPokemons.exists(userId, pokemonId)) {
            return TypedResult.Success(Unit)
        }

        localUserPokemons.upsert(
            UserPokemonEntity(
                userId = userId,
                pokemonId = pokemonId,
                createdAt = timestamp,
                updatedAt = timestamp,
            )
        )

        val userPokemonRemoteEntity =
            UserPokemonRemoteEntity(
                id = null,
                userId = userId,
                pokemonId = pokemonId,
                createdAt = timestamp,
                updatedAt = timestamp,
            )

        val remoteExistsResult =
            Retry.execute(
                body = { remoteUserPokemons.exists(userId, pokemonId) },
                retryIf = { it is FirebaseNetworkException },
            )

        remoteExistsResult.exceptionOrNull()?.let { exception ->
            if (exception !is FirebaseNetworkException) {
                crashlytics.recordExceptionWithKeys(
                    exception,
                    mapOf("userId" to userId, "pokemonId" to pokemonId.toString()),
                )
            }
        }

        val remoteExists = remoteExistsResult.getOrNull() == true
        if (!remoteExists) {
            val result =
                Retry.execute(
                    body = { remoteUserPokemons.upsert(userPokemonRemoteEntity) },
                    retryIf = { it is FirebaseNetworkException },
                )

            result.exceptionOrNull()?.let { exception ->
                analytics.logEvent(
                    "catch_error",
                    Bundle().apply {
                        putString("userId", userId)
                        putString("pokemonId", pokemonId.toString())
                    },
                )
            }
        }

        return TypedResult.Success(Unit)
    }

    override suspend fun resetForUser(userId: UserId) {
        localUserPokemons.deleteAllForUser(userId)

        val result =
            Retry.execute(
                body = { remoteUserPokemons.deleteAllForUser(userId) },
                retryIf = { it is FirebaseNetworkException },
            )

        result.exceptionOrNull()?.let { exception ->
            crashlytics.recordExceptionWithKeys(exception, mapOf("userId" to userId))
        }
    }
}
