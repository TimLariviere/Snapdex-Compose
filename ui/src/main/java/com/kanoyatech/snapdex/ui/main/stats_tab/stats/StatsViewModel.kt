package com.kanoyatech.snapdex.ui.main.stats_tab.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.datasources.LocalStatisticsDataSource
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class StatsViewModel(userFlow: Flow<User>, private val localStatistics: LocalStatisticsDataSource) :
    ViewModel() {
    var state by mutableStateOf(StatsState())
        private set

    init {
        userFlow
            .flatMapLatest { user ->
                val userId = user.id!!

                val completionRateFlow =
                    localStatistics.getCompletionRate(userId).onEach {
                        state = state.copy(overallCompletion = it)
                    }

                val completionRateByTypeFlow =
                    localStatistics.getCompletionRateByType(userId).onEach { statistics ->
                        state =
                            state.copy(
                                completionByType =
                                    PokemonType.entries.associate { type ->
                                        val statistic =
                                            statistics.find { it.type == type }!!.statistic
                                        type to statistic
                                    }
                            )
                    }

                combine(completionRateFlow, completionRateByTypeFlow) { _, _ -> }
            }
            .launchIn(viewModelScope)
    }
}
