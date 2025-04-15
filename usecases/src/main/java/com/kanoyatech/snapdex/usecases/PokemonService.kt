package com.kanoyatech.snapdex.usecases

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.datasources.DeleteAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.LocalPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.onError
import com.kanoyatech.snapdex.domain.providers.CrashReporter
import kotlinx.coroutines.flow.Flow

sealed interface CatchPokemonError {
    object CatchFailed : CatchPokemonError
}

class PokemonService(
    private val crashReporter: CrashReporter,
    private val localUserPokemons: LocalUserPokemonDataSource,
    private val localPokemons: LocalPokemonDataSource,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
) {
    fun getPokemonsCaughtByUser(userId: UserId): Flow<List<Pokemon>> {
        return localUserPokemons.observeAllForUser(userId)
    }

    suspend fun getById(pokemonId: PokemonId): Pokemon? {
        return localPokemons.getById(pokemonId)
    }

    suspend fun catchPokemon(
        userId: UserId,
        pokemonId: PokemonId,
    ): TypedResult<Unit, CatchPokemonError> {
        val timestamp = System.currentTimeMillis()

        // The user has already caught that pokemon, so we don't do anything
        if (localUserPokemons.exists(userId, pokemonId)) {
            return TypedResult.Success(Unit)
        }

        val value =
            Synced<PokemonId>(value = pokemonId, createdAt = timestamp, updatedAt = timestamp)

        localUserPokemons.upsert(userId, value)

        return TypedResult.Success(Unit)
    }

    suspend fun resetForUser(userId: UserId) {
        localUserPokemons.deleteAllForUser(userId)

        Retry.execute(
                body = { remoteUserPokemons.deleteAllForUser(userId) },
                mapFailure = { DeleteAllForRemoteUserError.Failure(it) },
                retryIf = { it is DeleteAllForRemoteUserError.NetworkError },
            )
            .onError { error ->
                when (error) {
                    DeleteAllForRemoteUserError.NetworkError -> Unit
                    is DeleteAllForRemoteUserError.Failure ->
                        crashReporter.recordException(error.exception)
                }
            }
    }
}
