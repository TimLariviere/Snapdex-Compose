package com.kanoyatech.snapdex.data.local.mappers

import com.kanoyatech.snapdex.data.local.dao.PokemonWithRelations
import com.kanoyatech.snapdex.domain.PokemonWeaknessCalculator
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonAbility
import com.kanoyatech.snapdex.domain.models.PokemonCategory
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import java.util.Locale

fun PokemonWithRelations.toPokemon(): Pokemon {
    val types = types.map { it.toType() }
    return Pokemon(
        id = pokemon.id,
        name = translations.associate { Locale.forLanguageTag(it.language) to it.name },
        description = translations.associate { Locale.forLanguageTag(it.language) to it.description },
        types = types,
        weaknesses = PokemonWeaknessCalculator.calculateWeaknesses(types),
        weight = (pokemon.weight / 10f).kg,
        height = (pokemon.height / 10f).m,
        category = PokemonCategory(
            id = category.category.id,
            name = category.translations.associate { Locale.forLanguageTag(it.language) to it.name }
        ),
        ability = PokemonAbility(
            id = ability.ability.id,
            name = ability.translations.associate { Locale.forLanguageTag(it.language) to it.name }
        ),
        maleToFemaleRatio = (pokemon.maleToFemaleRatio * 100.0).percent
    )
}