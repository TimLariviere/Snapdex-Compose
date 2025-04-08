package com.kanoyatech.snapdex.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import com.kanoyatech.snapdex.data.local.entities.AbilityEntity
import com.kanoyatech.snapdex.data.local.entities.AbilityTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.CategoryEntity
import com.kanoyatech.snapdex.data.local.entities.CategoryTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonTypeEntity

@Dao
interface PokemonDao {
    // Get all Pokémon with their translations in the specified language
    @Query("SELECT * FROM Pokemons")
    suspend fun getAll(): List<PokemonWithRelations>

    @Query("SELECT * FROM Pokemons WHERE id = :id")
    suspend fun getBy(id: Int): PokemonWithRelations?
}

// Define relationships between Pokémon and its related entities
class PokemonWithRelations {
    @Embedded
    lateinit var pokemon: PokemonEntity

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonTranslationEntity::class
    )
    lateinit var translations: List<PokemonTranslationEntity>

    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId",
        entity = PokemonTypeEntity::class
    )
    lateinit var types: List<PokemonTypeEntity>

    @Relation(
        entity = AbilityEntity::class,
        parentColumn = "abilityId",
        entityColumn = "id"
    )
    lateinit var ability: AbilityWithTranslation

    @Relation(
        entity = CategoryEntity::class,
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    lateinit var category: CategoryWithTranslation
}

// Define nested relationships for Ability and its translations
class AbilityWithTranslation {
    @Embedded
    lateinit var ability: AbilityEntity

    @Relation(
        parentColumn = "id",
        entityColumn = "abilityId"
    )
    lateinit var translations: List<AbilityTranslationEntity>
}

// Define nested relationships for Category and its translations
class CategoryWithTranslation {
    @Embedded
    lateinit var category: CategoryEntity

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    lateinit var translations: List<CategoryTranslationEntity>
}