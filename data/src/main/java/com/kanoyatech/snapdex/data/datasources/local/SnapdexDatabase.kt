package com.kanoyatech.snapdex.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanoyatech.snapdex.data.datasources.local.dao.EvolutionChainDao
import com.kanoyatech.snapdex.data.datasources.local.dao.PokemonDao
import com.kanoyatech.snapdex.data.datasources.local.dao.StatisticDao
import com.kanoyatech.snapdex.data.datasources.local.dao.UserDao
import com.kanoyatech.snapdex.data.datasources.local.dao.UserPokemonDao
import com.kanoyatech.snapdex.data.datasources.local.entities.AbilityEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.AbilityTranslationEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.CategoryEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.CategoryTranslationEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.EvolutionChainEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.EvolutionChainLinkEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.PokemonEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.PokemonTranslationEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.UserEntity
import com.kanoyatech.snapdex.data.datasources.local.entities.UserPokemonEntity

@Database(
    entities =
        [
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
            UserPokemonEntity::class,
        ],
    version = 1,
)
abstract class SnapdexDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
    abstract val evolutionChainDao: EvolutionChainDao
    abstract val userDao: UserDao
    abstract val userPokemonDao: UserPokemonDao
    abstract val statisticDao: StatisticDao
}
