package com.kanoyatech.snapdex.data.mappers

import com.kanoyatech.snapdex.data.dao.AbilityWithTranslation
import com.kanoyatech.snapdex.data.dao.CategoryWithTranslation
import com.kanoyatech.snapdex.data.dao.PokemonWithRelations
import com.kanoyatech.snapdex.data.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonAbility
import com.kanoyatech.snapdex.domain.PokemonCategory
import com.kanoyatech.snapdex.domain.PokemonType
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import java.util.Locale

fun PokemonWithRelations.toPokemon(locale: Locale): Pokemon {
    val translation = this.translations.find { it.language == locale.language }
    return Pokemon(
        id = this.pokemon.id,
        name = translation?.name ?: "NO_TRANSLATION",
        description = translation?.description ?: "NO_TRANSLATION",
        types = this.types.map { it.toPokemonType() },
        weaknesses = this.types.map { it.toPokemonType() },
        weight = this.pokemon.weight.kg,
        height = this.pokemon.height.m,
        category = this.category.toPokemonCategory(locale),
        ability = this.ability.toPokemonAbility(locale),
        maleToFemaleRatio = (this.pokemon.maleToFemaleRatio * 100.0).percent
    )
}

fun CategoryWithTranslation.toPokemonCategory(locale: Locale): PokemonCategory {
    val translation = this.translations.find { it.language == locale.language }
    return PokemonCategory(
        id = this.category.id,
        name = translation?.name ?: "NO_TRANSLATION"
    )
}

fun AbilityWithTranslation.toPokemonAbility(locale: Locale): PokemonAbility {
    val translation = this.translations.find { it.language == locale.language }
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