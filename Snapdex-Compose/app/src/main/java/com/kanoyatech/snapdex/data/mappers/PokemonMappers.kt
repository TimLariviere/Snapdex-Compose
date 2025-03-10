package com.kanoyatech.snapdex.data.mappers

import com.kanoyatech.snapdex.data.dao.AbilityWithTranslation
import com.kanoyatech.snapdex.data.dao.CategoryWithTranslation
import com.kanoyatech.snapdex.data.dao.PokemonWithRelations
import com.kanoyatech.snapdex.data.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonAbility
import com.kanoyatech.snapdex.domain.PokemonCategory
import com.kanoyatech.snapdex.domain.PokemonType
import com.kanoyatech.snapdex.domain.PokemonWeaknessCalculator
import com.kanoyatech.snapdex.domain.units.Length
import com.kanoyatech.snapdex.domain.units.Weight
import com.kanoyatech.snapdex.domain.units.percent
import java.util.Locale

fun PokemonWithRelations.toPokemon(locale: Locale): Pokemon {
    val translation = this.translations.find { it.language == locale.language }
    val types = this.types.map { it.toPokemonType() }
    return Pokemon(
        id = this.pokemon.id,
        name = translation?.name ?: "NO_TRANSLATION",
        description = translation?.description ?: "NO_TRANSLATION",
        types = types,
        weaknesses = PokemonWeaknessCalculator.calculateWeaknesses(types),
        weight = Weight.fromHectogram(this.pokemon.weight),
        height = Length.fromDecimeter(this.pokemon.height),
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

private fun toPokemonType(type: Int): PokemonType {
    val pokemonType =
        when (type) {
            0 -> PokemonType.BUG
            1 -> PokemonType.DARK
            2 -> PokemonType.DRAGON
            3 -> PokemonType.ELECTRIC
            4 -> PokemonType.FAIRY
            5 -> PokemonType.FIGHTING
            6 -> PokemonType.FIRE
            7 -> PokemonType.FLYING
            8 -> PokemonType.GHOST
            9 -> PokemonType.GRASS
            10 -> PokemonType.GROUND
            11 -> PokemonType.ICE
            12 -> PokemonType.NORMAL
            13 -> PokemonType.POISON
            14 -> PokemonType.PSYCHIC
            15 -> PokemonType.ROCK
            16 -> PokemonType.STEEL
            17 -> PokemonType.WATER
            else -> PokemonType.BUG
        }

    return pokemonType
}

fun PokemonTypeEntity.toPokemonType(): PokemonType {
    return toPokemonType(this.type)
}