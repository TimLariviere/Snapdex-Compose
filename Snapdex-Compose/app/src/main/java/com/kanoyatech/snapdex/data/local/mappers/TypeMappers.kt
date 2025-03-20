package com.kanoyatech.snapdex.data.local.mappers

import com.kanoyatech.snapdex.data.local.entities.PokemonTypeEntity
import com.kanoyatech.snapdex.domain.models.PokemonType

fun PokemonTypeEntity.toType(): PokemonType {
    return when (type) {
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
        else -> PokemonType.WATER
    }
}