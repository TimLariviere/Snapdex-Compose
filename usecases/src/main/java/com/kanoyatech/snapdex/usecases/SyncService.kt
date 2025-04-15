package com.kanoyatech.snapdex.usecases

import com.kanoyatech.snapdex.domain.datasources.GetAllForRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.GetRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.LocalUserDataSource
import com.kanoyatech.snapdex.domain.datasources.LocalUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.domain.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserError
import com.kanoyatech.snapdex.domain.datasources.UpsertRemoteUserPokemonError
import com.kanoyatech.snapdex.domain.onError
import com.kanoyatech.snapdex.domain.providers.AuthProvider
import com.kanoyatech.snapdex.domain.providers.CrashReporter

class SyncService(
    private val crashReporter: CrashReporter,
    private val authProvider: AuthProvider,
    private val localUsers: LocalUserDataSource,
    private val localUserPokemons: LocalUserPokemonDataSource,
    private val remoteUsers: RemoteUserDataSource,
    private val remoteUserPokemons: RemoteUserPokemonDataSource,
) {
    suspend fun syncData() {
        val userId = authProvider.getCurrentUserId() ?: return

        if (!syncUser(userId)) return

        syncUserPokemons(userId)
    }

    private suspend fun syncUser(userId: String): Boolean {
        val localUser = localUsers.getById(userId) ?: return false

        // Push local user to remote
        val remoteUser =
            Retry.execute(
                    body = { remoteUsers.getById(userId) },
                    mapFailure = { GetRemoteUserError.Failure(it) },
                    retryIf = { it == GetRemoteUserError.NetworkError },
                )
                .onError { error ->
                    when (error) {
                        GetRemoteUserError.NetworkError -> Unit
                        is GetRemoteUserError.Failure ->
                            crashReporter.recordException(error.exception)
                    }
                    return false
                }

        if (remoteUser == null || remoteUser.updatedAt < localUser.updatedAt) {
            Retry.execute(
                    body = { remoteUsers.upsert(localUser) },
                    mapFailure = { UpsertRemoteUserError.Failure(it) },
                    retryIf = { it == UpsertRemoteUserError.NetworkError },
                )
                .onError { error ->
                    when (error) {
                        UpsertRemoteUserError.NetworkError -> Unit
                        is UpsertRemoteUserError.Failure ->
                            crashReporter.recordException(error.exception)
                    }
                    return false
                }
        } else if (remoteUser.updatedAt > localUser.updatedAt) {
            val entity =
                localUser.copy(
                    value =
                        localUser.value.copy(
                            avatarId = remoteUser.value.avatarId,
                            name = remoteUser.value.name,
                        ),
                    updatedAt = remoteUser.updatedAt,
                )

            localUsers.upsert(entity)
        }

        return true
    }

    private suspend fun syncUserPokemons(userId: String) {
        val localPokemons = localUserPokemons.getAllForUser(userId)
        val remotePokemons =
            Retry.execute(
                    body = { remoteUserPokemons.getAllForUser(userId) },
                    mapFailure = { GetAllForRemoteUserError.Failure(it) },
                    retryIf = { it == GetAllForRemoteUserError.NetworkError },
                )
                .onError { error ->
                    when (error) {
                        GetAllForRemoteUserError.NetworkError -> Unit
                        is GetAllForRemoteUserError.Failure ->
                            crashReporter.recordException(error.exception)
                    }
                    return
                } ?: emptyList()

        val local = localPokemons.associateBy { it.value }
        val remote = remotePokemons.associateBy { it.value }

        val allKeys = local.keys union remote.keys
        val allPokemons =
            allKeys.map { key ->
                val localPokemon = local[key]
                val remotePokemon = remote[key]
                localPokemon to remotePokemon
            }

        allPokemons.forEach { (localPokemon, remotePokemon) ->
            when {
                localPokemon == null && remotePokemon != null -> {
                    localUserPokemons.upsert(userId, remotePokemon)
                }
                localPokemon != null && remotePokemon == null -> {
                    Retry.execute(
                            body = { remoteUserPokemons.upsert(userId, localPokemon) },
                            mapFailure = { UpsertRemoteUserPokemonError.Failure(it) },
                            retryIf = { it is UpsertRemoteUserPokemonError.NetworkError },
                        )
                        .onError { error ->
                            when (error) {
                                UpsertRemoteUserPokemonError.NetworkError -> Unit
                                is UpsertRemoteUserPokemonError.Failure ->
                                    crashReporter.recordException(error.exception)
                            }
                        }
                }
                localPokemon != null && remotePokemon != null -> {
                    when {
                        remotePokemon.updatedAt < localPokemon.updatedAt -> {
                            Retry.execute(
                                    body = { remoteUserPokemons.upsert(userId, localPokemon) },
                                    mapFailure = { UpsertRemoteUserPokemonError.Failure(it) },
                                    retryIf = { it is UpsertRemoteUserPokemonError.NetworkError },
                                )
                                .onError { error ->
                                    when (error) {
                                        UpsertRemoteUserPokemonError.NetworkError -> Unit
                                        is UpsertRemoteUserPokemonError.Failure ->
                                            crashReporter.recordException(error.exception)
                                    }
                                }
                        }
                        remotePokemon.updatedAt > localPokemon.updatedAt -> {
                            localUserPokemons.upsert(userId, remotePokemon)
                        }
                    }
                }
            }
        }
    }
}
