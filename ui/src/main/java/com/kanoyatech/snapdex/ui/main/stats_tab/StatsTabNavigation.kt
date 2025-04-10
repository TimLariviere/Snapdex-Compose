package com.kanoyatech.snapdex.ui.main.stats_tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kanoyatech.snapdex.ui.main.MainState
import com.kanoyatech.snapdex.ui.main.stats_tab.stats.StatsScreenRoot
import com.kanoyatech.snapdex.ui.main.stats_tab.stats.StatsViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable object StatsRoute

@Composable
fun StatsTabNavigation(
    paddingValues: PaddingValues,
    mainState: StateFlow<MainState>,
    shouldShowNavBar: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StatsRoute
    ) {
        composable<StatsRoute> {
            shouldShowNavBar(true)

            val userFlow = mainState.map { it.user }.filterNotNull()
            val viewModel: StatsViewModel = koinViewModel { parametersOf(userFlow) }
            StatsScreenRoot(
                paddingValues = paddingValues,
                viewModel = viewModel
            )
        }
    }
}