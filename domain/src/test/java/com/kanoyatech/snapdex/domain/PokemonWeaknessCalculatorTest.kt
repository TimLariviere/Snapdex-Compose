package com.kanoyatech.snapdex.domain

import com.kanoyatech.snapdex.domain.models.PokemonType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PokemonWeaknessCalculatorTest {
    @Test
    fun `Empty type list returns empty weakness list`() {
        val result = PokemonWeaknessCalculator.calculateWeaknesses(emptyList())
        assertTrue(result.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("singleType")
    fun `Single type has correct weaknesses`(type: PokemonType, expected: List<PokemonType>) {
        val actual = PokemonWeaknessCalculator.calculateWeaknesses(listOf(type))
        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("dualType")
    fun `Dual type has correct weaknesses after removing resistances`(
        types: List<PokemonType>,
        expected: List<PokemonType>,
    ) {
        val actual = PokemonWeaknessCalculator.calculateWeaknesses(types)
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun singleType() =
            listOf(
                Arguments.of(
                    PokemonType.FIRE,
                    listOf(PokemonType.GROUND, PokemonType.ROCK, PokemonType.WATER),
                ),
                Arguments.of(PokemonType.WATER, listOf(PokemonType.ELECTRIC, PokemonType.GRASS)),
            )

        @JvmStatic
        fun dualType() =
            listOf(
                Arguments.of(
                    listOf(PokemonType.FIRE, PokemonType.GRASS),
                    listOf(PokemonType.FLYING, PokemonType.POISON, PokemonType.ROCK),
                )
            )
    }
}
