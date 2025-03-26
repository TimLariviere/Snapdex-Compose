package com.kanoyatech.snapdex.data.repositories

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.kanoyatech.snapdex.data.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.entities.SyncStatus
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.data.local.mappers.toPokemon
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.repositories.CatchPokemonError
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.utils.Retry
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val localUserPokemons: UserPokemonDao,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
    private val localPokemons: PokemonDao
): PokemonRepository {
    override fun getPokemonsCaughtByUser(userId: UserId): Flow<List<Pokemon>> {
        return localUserPokemons.observeUserPokemons(userId)
            .map { userPokemons ->
                userPokemons.map { it.toPokemon() }
            }
    }

    override suspend fun getPokemonById(pokemonId: PokemonId): Pokemon? {
        val pokemonWithRelations = localPokemons.getBy(pokemonId)
            ?: return null

        return pokemonWithRelations.toPokemon()
    }

    override suspend fun catchPokemon(userId: UserId, pokemonId: PokemonId): TypedResult<Unit, CatchPokemonError> {
        val timestamp = System.currentTimeMillis()

        // The user has already caught that pokemon, so we don't do anything
        if (localUserPokemons.exists(userId, pokemonId)) {
            return TypedResult.Success(Unit)
        }

        localUserPokemons.insert(
            UserPokemonEntity(
                userId = userId,
                pokemonId = pokemonId,
                createdAt = timestamp,
                syncStatus = SyncStatus.PENDING
            )
        )

        val userPokemonRemoteEntity = UserPokemonRemoteEntity(
            userId = userId,
            pokemonId = pokemonId,
            createdAt = timestamp
        )

        val remoteExistsResult = Retry.execute(
            body = { remoteUserPokemons.exists(userId, pokemonId) },
            retryIf = { it is FirebaseNetworkException }
        )

        val remoteExists = remoteExistsResult.getOrNull() ?: false
        if (!remoteExists) {
            val result = Retry.execute(
                body = { remoteUserPokemons.insert(userPokemonRemoteEntity) },
                retryIf = { it is FirebaseNetworkException }
            )

            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                Log.d("Firestore", "Error while inserting UserPokemon: $e")
            }
        }

        return TypedResult.Success(Unit)
    }

    override suspend fun resetForUser(userId: UserId): TypedResult<Unit, Unit> {
        Retry.execute(
            body = { remoteUserPokemons.deleteAllForUser(userId) },
            retryIf = { it is FirebaseNetworkException }
        )

        localUserPokemons.deleteAllForUser(userId)

        return TypedResult.Success(Unit)
    }
}