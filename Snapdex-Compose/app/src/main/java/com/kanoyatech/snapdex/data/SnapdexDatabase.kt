package com.kanoyatech.snapdex.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanoyatech.snapdex.data.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.dao.PokemonDao
import com.kanoyatech.snapdex.data.dao.UserDao
import com.kanoyatech.snapdex.data.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.entities.AbilityEntity
import com.kanoyatech.snapdex.data.entities.AbilityTranslationEntity
import com.kanoyatech.snapdex.data.entities.CategoryEntity
import com.kanoyatech.snapdex.data.entities.CategoryTranslationEntity
import com.kanoyatech.snapdex.data.entities.EvolutionChainEntity
import com.kanoyatech.snapdex.data.entities.EvolutionChainLinkEntity
import com.kanoyatech.snapdex.data.entities.PokemonEntity
import com.kanoyatech.snapdex.data.entities.PokemonTranslationEntity
import com.kanoyatech.snapdex.data.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.data.entities.UserEntity
import com.kanoyatech.snapdex.data.entities.UserPokemonEntity

@Database(
    entities = [
        AbilityEntity::class,
        AbilityTranslationEntity::class,
        CategoryEntity::class,
        CategoryTranslationEntity::class,
        PokemonEntity::class,
        PokemonTranslationEntity::class,
        PokemonTypeEntity::class,
        EvolutionChainEntity::class,
        EvolutionChainLinkEntity::class,
        UserEntity::class,
        UserPokemonEntity::class
    ],
    version = 1
)
abstract class SnapdexDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val evolutionChainDao: EvolutionChainDao
    abstract val userDao: UserDao
    abstract val userPokemonDao: UserPokemonDao
}