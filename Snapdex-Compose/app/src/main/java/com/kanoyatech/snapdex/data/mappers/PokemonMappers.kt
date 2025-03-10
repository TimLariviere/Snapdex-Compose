package com.kanoyatech.snapdex.data.mappers

import com.kanoyatech.snapdex.data.dao.PokemonDao
import com.kanoyatech.snapdex.data.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonAbility
import com.kanoyatech.snapdex.domain.PokemonCategory
import com.kanoyatech.snapdex.domain.PokemonType
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent

fun PokemonDao.PokemonWithRelations.toPokemon(): Pokemon {
    val translation = this.translations.firstOrNull()
    return Pokemon(
        id = this.pokemon.id,
        name = translation?.name ?: "NO_TRANSLATION",
        description = translation?.description ?: "NO_TRANSLATION",
        types = this.types.map { it.toPokemonType() },
        weaknesses = this.types.map { it.toPokemonType() },
        weight = this.pokemon.weight.kg,
        height = this.pokemon.height.m,
        category = this.category.toPokemonCategory(),
        ability = this.ability.toPokemonAbility(),
        maleToFemaleRatio = (this.pokemon.maleToFemaleRatio * 100.0).percent
    )
}

fun PokemonDao.CategoryWithTranslation.toPokemonCategory(): PokemonCategory {
    val translation = this.translations.firstOrNull()
    return PokemonCategory(
        id = this.category.id,
        name = translation?.name ?: "NO_TRANSLATION"
    )
}

fun PokemonDao.AbilityWithTranslation.toPokemonAbility(): PokemonAbility {
    val translation = this.translations.firstOrNull()
    return PokemonAbility(
        id = this.ability.id,
        name = translation?.name ?: "NO_TRANSLATION"
    )
}

fun PokemonTypeEntity.toPokemonType(): PokemonType {
    val pokemonType =
        when (this.type) {
            else -> PokemonType.BUG
        }

    return pokemonType
}