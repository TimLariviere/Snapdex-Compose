package com.kanoyatech.snapdex.domain.models

enum class PokemonType {
    BUG,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER;

    companion object {
        fun fromInt(value: Int): PokemonType {
            return when (value) {
                0 -> BUG
                1 -> DRAGON
                2 -> ELECTRIC
                3 -> FAIRY
                4 -> FIGHTING
                5 -> FIRE
                6 -> FLYING
                7 -> GHOST
                8 -> GRASS
                9 -> GROUND
                10 -> ICE
                11 -> NORMAL
                12 -> POISON
                13 -> PSYCHIC
                14 -> ROCK
                15 -> STEEL
                16 -> WATER
                else -> BUG
            }
        }
    }
}