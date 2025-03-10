package com.kanoyatech.snapdex.domain

object PokemonWeaknessCalculator {
    private val weaknessesMap = mapOf(
        Pair(PokemonType.BUG, listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.ROCK)),
        Pair(PokemonType.DARK, listOf(PokemonType.FIGHTING, PokemonType.BUG, PokemonType.FAIRY)),
        Pair(PokemonType.DRAGON, listOf(PokemonType.ICE, PokemonType.DRAGON, PokemonType.FAIRY)),
        Pair(PokemonType.ELECTRIC, listOf(PokemonType.GROUND)),
        Pair(PokemonType.FAIRY, listOf(PokemonType.STEEL, PokemonType.POISON)),
        Pair(PokemonType.FIGHTING, listOf(PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.FAIRY)),
        Pair(PokemonType.FIRE, listOf(PokemonType.WATER, PokemonType.ROCK, PokemonType.GROUND)),
        Pair(PokemonType.FLYING, listOf(PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.ICE)),
        Pair(PokemonType.GHOST, listOf(PokemonType.GHOST, PokemonType.DARK)),
        Pair(PokemonType.GRASS, listOf(PokemonType.FIRE, PokemonType.ICE, PokemonType.FLYING, PokemonType.BUG, PokemonType.POISON)),
        Pair(PokemonType.GROUND, listOf(PokemonType.WATER, PokemonType.ICE, PokemonType.GRASS)),
        Pair(PokemonType.ICE, listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.ROCK, PokemonType.STEEL)),
        Pair(PokemonType.NORMAL, listOf(PokemonType.FIGHTING)),
        Pair(PokemonType.POISON, listOf(PokemonType.GROUND, PokemonType.PSYCHIC)),
        Pair(PokemonType.PSYCHIC, listOf(PokemonType.BUG, PokemonType.GHOST, PokemonType.DARK)),
        Pair(PokemonType.ROCK, listOf(PokemonType.WATER, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.STEEL)),
        Pair(PokemonType.STEEL, listOf(PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.GROUND)),
        Pair(PokemonType.WATER, listOf(PokemonType.ELECTRIC, PokemonType.GRASS))
    )

    private val resistancesMap = mapOf(
        Pair(PokemonType.BUG, listOf(PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.GROUND)),
        Pair(PokemonType.DARK, listOf(PokemonType.GHOST, PokemonType.DARK)),
        Pair(PokemonType.DRAGON, listOf(PokemonType.FIRE, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.GRASS)),
        Pair(PokemonType.ELECTRIC, listOf(PokemonType.ELECTRIC, PokemonType.FLYING, PokemonType.STEEL)),
        Pair(PokemonType.FAIRY, listOf(PokemonType.FIGHTING, PokemonType.BUG, PokemonType.DARK)),
        Pair(PokemonType.FIGHTING, listOf(PokemonType.BUG, PokemonType.ROCK, PokemonType.DARK)),
        Pair(PokemonType.FIRE, listOf(PokemonType.FIRE, PokemonType.GRASS, PokemonType.ICE, PokemonType.BUG, PokemonType.STEEL, PokemonType.FAIRY)),
        Pair(PokemonType.FLYING, listOf(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG)),
        Pair(PokemonType.GHOST, listOf(PokemonType.POISON, PokemonType.BUG)),
        Pair(PokemonType.GRASS, listOf(PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.GRASS, PokemonType.GROUND)),
        Pair(PokemonType.GROUND, listOf(PokemonType.POISON, PokemonType.ROCK)),
        Pair(PokemonType.ICE, listOf(PokemonType.ICE)),
        Pair(PokemonType.POISON, listOf(PokemonType.GRASS, PokemonType.FLYING, PokemonType.POISON, PokemonType.FAIRY)),
        Pair(PokemonType.PSYCHIC, listOf(PokemonType.FIGHTING, PokemonType.PSYCHIC)),
        Pair(PokemonType.ROCK, listOf(PokemonType.NORMAL, PokemonType.FIRE, PokemonType.POISON, PokemonType.FLYING)),
        Pair(PokemonType.STEEL, listOf(PokemonType.NORMAL, PokemonType.GRASS, PokemonType.ICE, PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.BUG, PokemonType.ROCK, PokemonType.DRAGON, PokemonType.STEEL, PokemonType.FAIRY)),
        Pair(PokemonType.WATER, listOf(PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL))
    )

    private val immunitiesMap = mapOf(
        Pair(PokemonType.DARK, listOf(PokemonType.PSYCHIC)),
        Pair(PokemonType.FAIRY, listOf(PokemonType.DRAGON)),
        Pair(PokemonType.FLYING, listOf(PokemonType.GROUND)),
        Pair(PokemonType.GHOST, listOf(PokemonType.NORMAL, PokemonType.FIGHTING)),
        Pair(PokemonType.GROUND, listOf(PokemonType.ELECTRIC)),
        Pair(PokemonType.NORMAL, listOf(PokemonType.GHOST)),
        Pair(PokemonType.STEEL, listOf(PokemonType.POISON))
    )

    fun calculateWeaknesses(types: List<PokemonType>): List<PokemonType> {
        val actualWeaknesses = mutableListOf<PokemonType>()

        types.forEach { type ->
            weaknessesMap[type]?.let { weaknesses ->
                actualWeaknesses.addAll(weaknesses)
            }
        }

        types.forEach { type ->
            immunitiesMap[type]?.let { immunities ->
                actualWeaknesses.removeAll(immunities)
            }

            resistancesMap[type]?.let { resistances ->
                actualWeaknesses.removeAll(resistances)
            }
        }

        return actualWeaknesses.distinct().sorted()
    }
}