package com.kanoyatech.snapdex.data.datasources.remote

import com.google.firebase.FirebaseNetworkException
import com.kanoyatech.snapdex.data.datasources.remote.dao.RemoteUserPokemonDao
import com.kanoyatech.snapdex.data.datasources.remote.mappers.toSyncedPokemon
import com.kanoyatech.snapdex.data.datasources.remote.mappers.toUserRemotePokemonEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.datasources.DeleteAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.GetAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserPokemonError
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId

class FirebaseRemoteUserPokemonDataSource(private val remoteUserPokemons: RemoteUserPokemonDao) :
    RemoteUserPokemonDataSource {
    override suspend fun getAllForUser(
        userId: UserId
    ): TypedResult<List<Synced<PokemonId>>, GetAllForRemoteUserError> {
        try {
            val value = remoteUserPokemons.getAllForUser(userId).map { it.toSyncedPokemon() }
            return TypedResult.Success(value)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(GetAllForRemoteUserError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(GetAllForRemoteUserError.Failure(ex))
        }
    }

    override suspend fun upsert(
        userId: UserId,
        pokemon: Synced<PokemonId>,
    ): TypedResult<Unit, UpsertRemoteUserPokemonError> {
        try {
            val entity = pokemon.toUserRemotePokemonEntity(userId)
            remoteUserPokemons.upsert(entity)
            return TypedResult.Success(Unit)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(UpsertRemoteUserPokemonError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(UpsertRemoteUserPokemonError.Failure(ex))
        }
    }

    override suspend fun deleteAllForUser(
        userId: UserId
    ): TypedResult<Unit, DeleteAllForRemoteUserError> {
        try {
            val value = remoteUserPokemons.deleteAllForUser(userId)
            return TypedResult.Success(value)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(DeleteAllForRemoteUserError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(DeleteAllForRemoteUserError.Failure(ex))
        }
    }
}
