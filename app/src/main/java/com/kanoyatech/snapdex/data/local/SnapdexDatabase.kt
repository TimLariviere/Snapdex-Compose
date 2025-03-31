package com.kanoyatech.snapdex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanoyatech.snapdex.data.local.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.local.dao.StatisticDao
import com.kanoyatech.snapdex.data.local.dao.UserDao
import com.kanoyatech.snapdex.data.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.local.entities.AbilityEntity
import com.kanoyatech.snapdex.data.local.entities.AbilityTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.CategoryEntity
import com.kanoyatech.snapdex.data.local.entities.CategoryTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.EvolutionChainEntity
import com.kanoyatech.snapdex.data.local.entities.EvolutionChainLinkEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonTranslationEntity
import com.kanoyatech.snapdex.data.local.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.data.local.entities.UserEntity
import com.kanoyatech.snapdex.data.local.entities.UserPokemonEntity

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
    abstract val statisticDao: StatisticDao
}