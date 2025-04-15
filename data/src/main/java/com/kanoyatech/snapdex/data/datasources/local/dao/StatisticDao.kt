package com.kanoyatech.snapdex.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {
    @Query(
        """
        SELECT
            (SELECT COUNT(*) FROM Pokemons) AS totalPokemonCount,
            COUNT(DISTINCT up.pokemonId) AS caughtPokemonCount
        FROM
            UserPokemons up
        WHERE
            up.userId = :userId
    """
    )
    fun getCompletionRate(userId: String): Flow<CompletionRate>

    @Query(
        """
        WITH TypeCounts AS (
            SELECT 
                pt.type,
                COUNT(DISTINCT p.id) AS totalPokemonCount,
                COUNT(DISTINCT up.pokemonId) AS caughtPokemonCount
            FROM 
                PokemonTypes pt
            JOIN 
                Pokemons p ON pt.pokemonId = p.id
            LEFT JOIN 
                UserPokemons up ON p.id = up.pokemonId AND up.userId = :userId
            GROUP BY 
                pt.type
        )
        
        SELECT 
            type,
            totalPokemonCount,
            COALESCE(caughtPokemonCount, 0) AS caughtPokemonCount
        FROM 
            TypeCounts
    """
    )
    fun getCompletionRateByType(userId: String): Flow<List<CompletionRateByType>>
}

class CompletionRate {
    var totalPokemonCount: Int = 0
    var caughtPokemonCount: Int = 0
}

class CompletionRateByType {
    var type: Int = 0
    var totalPokemonCount: Int = 0
    var caughtPokemonCount: Int = 0
}
