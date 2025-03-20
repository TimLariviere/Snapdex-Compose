package com.kanoyatech.snapdex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.UserId

@Dao
interface UserPokemonDao {
    @Insert
    suspend fun insert(entity: UserPokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<UserPokemonEntity>)

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