package com.kanoyatech.snapdex.data.repositories

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.data.local.dao.UserDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserDataSource
import com.kanoyatech.snapdex.data.remote.datasources.RemoteUserPokemonDataSource
import com.kanoyatech.snapdex.data.remote.entities.UserPokemonRemoteEntity
import com.kanoyatech.snapdex.data.remote.entities.UserRemoteEntity
import com.kanoyatech.snapdex.domain.repositories.SyncRepository
import com.kanoyatech.snapdex.data.Retry
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity

class SyncRepositoryImpl(
    private val auth: FirebaseAuth,
    private val localUsers: UserDao,
    private val localUserPokemons: UserPokemonDao,
    private val remoteUsers: RemoteUserDataSource,
    private val remoteUserPokemons: RemoteUserPokemonDataSource
): SyncRepository {
    override suspend fun syncData() {
        val userId = auth.currentUser?.uid
            ?: return

        if (!syncUser(userId))
            return

        syncUserPokemons(userId)
    }

    private suspend fun syncUser(userId: String): Boolean {
        val localUser = localUsers.getById(userId)
            ?: return false

        // Push local user to remote
        val remoteUser = remoteUsers.get(userId)
        if (remoteUser == null || remoteUser.updatedAt < localUser.updatedAt) {
            val entity = UserRemoteEntity(
                id = userId,
                avatarId = localUser.avatarId,
                name = localUser.name,
                createdAt = localUser.createdAt,
                updatedAt = localUser.updatedAt
            )

            Retry.execute(
                body = { remoteUsers.upsert(entity) },
                retryIf = { it is FirebaseNetworkException }
            )
        } else if (remoteUser.updatedAt > localUser.updatedAt) {
            val entity = localUser.copy(
                avatarId = remoteUser.avatarId,
                name = remoteUser.name,
                updatedAt = remoteUser.updatedAt
            )

            localUsers.upsert(entity)
        }

        return true
    }

    private suspend fun syncUserPokemons(userId: String) {
        val localPokemons = localUserPokemons.getAllForUser(userId)
        val remotePokemons = remoteUserPokemons.getAllForUser(userId)

        val local = localPokemons.associateBy { it.pokemonId }
        val remote = remotePokemons.associateBy { it.pokemonId }

        val allKeys = local.keys union remote.keys
        val allPokemons = allKeys.map { key ->
            val localPokemon = local[key]
            val remotePokemon = remote[key]
            localPokemon to remotePokemon
        }

        allPokemons.forEach { (localPokemon, remotePokemon) ->
            when {
                localPokemon == null && remotePokemon != null -> {
                    val entity = UserPokemonEntity(
                        userId = remotePokemon.userId,
                        pokemonId = remotePokemon.pokemonId,
                        createdAt = remotePokemon.createdAt,
                        updatedAt = remotePokemon.updatedAt
                    )

                    localUserPokemons.upsert(entity)
                }
                localPokemon != null && remotePokemon == null -> {
                    val entity = UserPokemonRemoteEntity(
                        id = null,
                        userId = localPokemon.userId,
                        pokemonId = localPokemon.pokemonId,
                        createdAt = localPokemon.createdAt,
                        updatedAt = localPokemon.updatedAt
                    )

                    Retry.execute(
                        body = { remoteUserPokemons.upsert(entity) },
                        retryIf = { it is FirebaseNetworkException }
                    )
                }
                localPokemon != null && remotePokemon != null -> {
                    when {
                        remotePokemon.updatedAt < localPokemon.updatedAt -> {
                            val entity = remotePokemon.copy(
                                createdAt = localPokemon.createdAt,
                                updatedAt = localPokemon.updatedAt
                            )

                            Retry.execute(
                                body = { remoteUserPokemons.upsert(entity) },
                                retryIf = { it is FirebaseNetworkException }
                            )
                        }
                        remotePokemon.updatedAt > localPokemon.updatedAt -> {
                            val entity = localPokemon.copy(
                                createdAt = remotePokemon.createdAt,
                                updatedAt = remotePokemon.updatedAt
                            )

                            localUserPokemons.upsert(entity)
                        }
                    }
                }
            }
        }
    }
}