package com.kanoyatech.snapdex.domain.datasources

import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.models.UserId

sealed interface GetRemoteUserError {
    data class Failure(val exception: Throwable) : GetRemoteUserError

    object NetworkError : GetRemoteUserError
}

sealed interface UpsertRemoteUserError {
    data class Failure(val exception: Throwable) : UpsertRemoteUserError

    object NetworkError : UpsertRemoteUserError
}

sealed interface DeleteRemoteUserError {
    data class Failure(val exception: Throwable) : DeleteRemoteUserError

    object NetworkError : DeleteRemoteUserError
}

interface RemoteUserDataSource {
    suspend fun getById(userId: UserId): TypedResult<Synced<User>?, GetRemoteUserError>

    suspend fun upsert(user: Synced<User>): TypedResult<Unit, UpsertRemoteUserError>

    suspend fun delete(userId: UserId): TypedResult<Unit, DeleteRemoteUserError>
}
