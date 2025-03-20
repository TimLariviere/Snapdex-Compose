package com.kanoyatech.snapdex.data.local.mappers

import com.kanoyatech.snapdex.data.local.dao.PokemonWithRelations
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonAbility
import com.kanoyatech.snapdex.domain.models.PokemonCategory
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import java.util.Locale

fun PokemonWithRelations.toPokemon(locale: Locale): Pokemon {
    val translation = translations.firstOrNull { it.language == locale.language }
    val categoryTranslation = category.translations.firstOrNull { it.language == locale.language }
    val abilityTranslation = ability.translations.firstOrNull { it.language == locale.language }
    return Pokemon(
        id = pokemon.id,
        name = translation?.name ?: "NO_TRANSLATION",
        description = translation?.description ?: "NO_TRANSLATION",
        types = types.map { it.toType() },
        weaknesses = types.map { it.toType() },
        weight = (pokemon.weight / 10f).kg,
        height = (pokemon.height / 10f).m,
        category = PokemonCategory(
            id = category.category.id,
            name = categoryTranslation?.name ?: "NO_TRANSLATION"
        ),
        ability = PokemonAbility(
            id = ability.ability.id,
            name = abilityTranslation?.name ?: "NO_TRANSLATION"
        ),
        maleToFemaleRatio = pokemon.maleToFemaleRatio.percent
    )
}