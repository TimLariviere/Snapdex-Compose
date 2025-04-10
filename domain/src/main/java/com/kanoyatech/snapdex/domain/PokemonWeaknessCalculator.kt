package com.kanoyatech.snapdex.domain

import com.kanoyatech.snapdex.domain.models.PokemonType

object PokemonWeaknessCalculator {
    private val weaknessesMap =
        mapOf(
            PokemonType.BUG to listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.ROCK),
            PokemonType.DRAGON to listOf(PokemonType.ICE, PokemonType.DRAGON, PokemonType.FAIRY),
            PokemonType.ELECTRIC to listOf(PokemonType.GROUND),
            PokemonType.FAIRY to listOf(PokemonType.STEEL, PokemonType.POISON),
            PokemonType.FIGHTING to
                listOf(PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.FAIRY),
            PokemonType.FIRE to listOf(PokemonType.WATER, PokemonType.ROCK, PokemonType.GROUND),
            PokemonType.FLYING to listOf(PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.ICE),
            PokemonType.GHOST to listOf(PokemonType.GHOST),
            PokemonType.GRASS to
                listOf(
                    PokemonType.FIRE,
                    PokemonType.ICE,
                    PokemonType.FLYING,
                    PokemonType.BUG,
                    PokemonType.POISON,
                ),
            PokemonType.GROUND to listOf(PokemonType.WATER, PokemonType.ICE, PokemonType.GRASS),
            PokemonType.ICE to
                listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.ROCK, PokemonType.STEEL),
            PokemonType.NORMAL to listOf(PokemonType.FIGHTING),
            PokemonType.POISON to listOf(PokemonType.GROUND, PokemonType.PSYCHIC),
            PokemonType.PSYCHIC to listOf(PokemonType.BUG, PokemonType.GHOST),
            PokemonType.ROCK to
                listOf(
                    PokemonType.WATER,
                    PokemonType.GRASS,
                    PokemonType.FIGHTING,
                    PokemonType.GROUND,
                    PokemonType.STEEL,
                ),
            PokemonType.STEEL to listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.GROUND),
            PokemonType.WATER to listOf(PokemonType.ELECTRIC, PokemonType.GRASS),
        )

    private val resistancesMap =
        mapOf(
            PokemonType.BUG to listOf(PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.GROUND),
            PokemonType.DRAGON to
                listOf(
                    PokemonType.FIRE,
                    PokemonType.WATER,
                    PokemonType.ELECTRIC,
                    PokemonType.GRASS,
                ),
            PokemonType.ELECTRIC to
                listOf(PokemonType.ELECTRIC, PokemonType.FLYING, PokemonType.STEEL),
            PokemonType.FAIRY to listOf(PokemonType.FIGHTING, PokemonType.BUG),
            PokemonType.FIGHTING to listOf(PokemonType.BUG, PokemonType.ROCK),
            PokemonType.FIRE to
                listOf(
                    PokemonType.FIRE,
                    PokemonType.GRASS,
                    PokemonType.ICE,
                    PokemonType.BUG,
                    PokemonType.STEEL,
                    PokemonType.FAIRY,
                ),
            PokemonType.FLYING to listOf(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG),
            PokemonType.GHOST to listOf(PokemonType.POISON, PokemonType.BUG),
            PokemonType.GRASS to
                listOf(
                    PokemonType.WATER,
                    PokemonType.ELECTRIC,
                    PokemonType.GRASS,
                    PokemonType.GROUND,
                ),
            PokemonType.GROUND to listOf(PokemonType.POISON, PokemonType.ROCK),
            PokemonType.ICE to listOf(PokemonType.ICE),
            PokemonType.POISON to
                listOf(
                    PokemonType.GRASS,
                    PokemonType.FLYING,
                    PokemonType.POISON,
                    PokemonType.FAIRY,
                ),
            PokemonType.PSYCHIC to listOf(PokemonType.FIGHTING, PokemonType.PSYCHIC),
            PokemonType.ROCK to
                listOf(
                    PokemonType.NORMAL,
                    PokemonType.FIRE,
                    PokemonType.POISON,
                    PokemonType.FLYING,
                ),
            PokemonType.STEEL to
                listOf(
                    PokemonType.NORMAL,
                    PokemonType.GRASS,
                    PokemonType.ICE,
                    PokemonType.FLYING,
                    PokemonType.PSYCHIC,
                    PokemonType.BUG,
                    PokemonType.ROCK,
                    PokemonType.DRAGON,
                    PokemonType.STEEL,
                    PokemonType.FAIRY,
                ),
            PokemonType.WATER to
                listOf(PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL),
        )

    private val immunitiesMap =
        mapOf(
            PokemonType.FAIRY to listOf(PokemonType.DRAGON),
            PokemonType.FLYING to listOf(PokemonType.GROUND),
            PokemonType.GHOST to listOf(PokemonType.NORMAL, PokemonType.FIGHTING),
            PokemonType.GROUND to listOf(PokemonType.ELECTRIC),
            PokemonType.NORMAL to listOf(PokemonType.GHOST),
            PokemonType.STEEL to listOf(PokemonType.POISON),
        )

    fun calculateWeaknesses(types: List<PokemonType>): List<PokemonType> {
        val actualWeaknesses = mutableListOf<PokemonType>()

        types.forEach { type ->
            weaknessesMap[type]?.let { weaknesses -> actualWeaknesses.addAll(weaknesses) }
        }

        types.forEach { type ->
            immunitiesMap[type]?.let { immunities -> actualWeaknesses.removeAll(immunities) }

            resistancesMap[type]?.let { resistances -> actualWeaknesses.removeAll(resistances) }
        }

        return actualWeaknesses.distinct().sorted()
    }
}
