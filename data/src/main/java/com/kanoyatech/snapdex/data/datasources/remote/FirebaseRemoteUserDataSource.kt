package com.kanoyatech.snapdex.data.datasources.remote

import com.google.firebase.FirebaseNetworkException
import com.kanoyatech.snapdex.data.datasources.remote.dao.RemoteUserDao
import com.kanoyatech.snapdex.data.datasources.remote.mappers.toSyncedUser
import com.kanoyatech.snapdex.data.datasources.remote.mappers.toUserRemoteEntity
import com.kanoyatech.snapdex.domain.Synced
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.datasources.DeleteRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.GetRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserError
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.models.UserId

class FirebaseRemoteUserDataSource(private val remoteUsers: RemoteUserDao) : RemoteUserDataSource {
    override suspend fun getById(userId: UserId): TypedResult<Synced<User>?, GetRemoteUserError> {
        try {
            val user = remoteUsers.get(userId)?.toSyncedUser()
            return TypedResult.Success(user)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(GetRemoteUserError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(GetRemoteUserError.Failure(ex))
        }
    }

    override suspend fun upsert(user: Synced<User>): TypedResult<Unit, UpsertRemoteUserError> {
        try {
            val entity = user.toUserRemoteEntity()
            remoteUsers.upsert(entity)
            return TypedResult.Success(Unit)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(UpsertRemoteUserError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(UpsertRemoteUserError.Failure(ex))
        }
    }

    override suspend fun delete(userId: UserId): TypedResult<Unit, DeleteRemoteUserError> {
        try {
            val user = remoteUsers.delete(userId)
            return TypedResult.Success(user)
        } catch (_: FirebaseNetworkException) {
            return TypedResult.Error(DeleteRemoteUserError.NetworkError)
        } catch (ex: Exception) {
            return TypedResult.Error(DeleteRemoteUserError.Failure(ex))
        }
    }
}
