package com.kanoyatech.snapdex.domain.models

data class Statistic(
    val totalPokemonCount: Int,
    val caughtPokemonCount: Int
) {
    companion object {
        val zero: Statistic get() { return Statistic(totalPokemonCount = 1, caughtPokemonCount = 0) }
    }
}

data class StatisticByType(
    val type: PokemonType,
    val statistic: Statistic
)