package com.kanoyatech.snapdex.domain.models

data class Statistic(val totalPokemonCount: Int, val caughtPokemonCount: Int)

data class StatisticByType(val type: PokemonType, val statistic: Statistic)
