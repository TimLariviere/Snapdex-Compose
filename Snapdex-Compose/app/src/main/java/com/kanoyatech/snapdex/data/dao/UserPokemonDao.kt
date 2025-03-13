package com.kanoyatech.snapdex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kanoyatech.snapdex.data.entities.UserPokemonEntity
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.domain.UserId

@Dao
interface UserPokemonDao {
    @Insert
    suspend fun insert(entity: UserPokemonEntity)

    @Query("SELECT 1 FROM UserPokemons WHERE userId = :userId AND pokemonId = :pokemonId")
    suspend fun exists(userId: UserId, pokemonId: PokemonId): Boolean

    @Query("""
        SELECT * FROM Pokemons
        WHERE id IN (
            SELECT pokemonId FROM UserPokemons
            WHERE userId = :userId
        )
    """)
    suspend fun getUserPokemons(userId: UserId): List<PokemonWithRelations>
}