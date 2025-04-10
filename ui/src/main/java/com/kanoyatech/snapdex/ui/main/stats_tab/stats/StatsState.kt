package com.kanoyatech.snapdex.ui.main.stats_tab.stats

import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.models.Statistic

data class StatsState(
    val overallCompletion: Statistic = Statistic(totalPokemonCount = 1, caughtPokemonCount = 0),
    val completionByType: Map<PokemonType, Statistic> = PokemonType.entries.associate {
        it to Statistic(totalPokemonCount = 1, caughtPokemonCount = 0)
    }
)
