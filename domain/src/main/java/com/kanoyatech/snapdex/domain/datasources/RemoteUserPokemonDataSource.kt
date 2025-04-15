package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId

sealed interface GetAllForRemoteUserError {
    data class Failure(val exception: Throwable) : GetAllForRemoteUserError

    object NetworkError : GetAllForRemoteUserError
}

sealed interface UpsertRemoteUserPokemonError {
    data class Failure(val exception: Throwable) : UpsertRemoteUserPokemonError

    object NetworkError : UpsertRemoteUserPokemonError
}

sealed interface DeleteAllForRemoteUserError {
    data class Failure(val exception: Throwable) : DeleteAllForRemoteUserError

    object NetworkError : DeleteAllForRemoteUserError
}

interface RemoteUserPokemonDataSource {
    suspend fun getAllForUser(
        userId: UserId
    ): TypedResult<List<Synced<PokemonId>>, GetAllForRemoteUserError>

    suspend fun upsert(
        userId: UserId,
        pokemon: Synced<PokemonId>,
    ): TypedResult<Unit, UpsertRemoteUserPokemonError>

    suspend fun deleteAllForUser(userId: UserId): TypedResult<Unit, DeleteAllForRemoteUserError>
}
