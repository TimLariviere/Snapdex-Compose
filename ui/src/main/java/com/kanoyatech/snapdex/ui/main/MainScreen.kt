package com.kanoyatech.snapdex.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.components.SnapdexCircularProgressIndicator
import com.kanoyatech.snapdex.designsystem.components.SnapdexNavBar
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.TabItem
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.main.pokedex_tab.PokedexTabNavigation
import com.kanoyatech.snapdex.ui.main.profile_tab.ProfileTabNavigation
import com.kanoyatech.snapdex.ui.main.stats_tab.StatsTabNavigation
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel

enum class ActiveTab {
    POKEDEX,
    STATS,
    PROFILE,
}

@Composable
fun MainScreenRoot(viewModel: MainViewModel = koinViewModel(), onLoggedOut: () -> Unit) {
    MainScreen(
        stateFlow = viewModel.state,
        onAction = { action ->
            when (action) {
                MainAction.OnLoggedOut -> onLoggedOut()
            }
        },
    )
}

@Composable
fun MainScreen(stateFlow: StateFlow<MainState>, onAction: (MainAction) -> Unit) {
    val state = stateFlow.collectAsState()
    val activeTab = remember { mutableStateOf(ActiveTab.POKEDEX) }
    val shouldShowNavBar = remember { mutableStateOf(true) }

    SnapdexScaffold { paddingValues ->
        val adjustedPaddingValues =
            remember(paddingValues) {
                PaddingValues(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top =
                        (paddingValues.calculateTopPadding() - 16.dp).coerceAtLeast(
                            0.dp
                        ), // Not sure why Scaffold has a top padding that is too large
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding() + 48.dp,
                )
            }

        Box(modifier = Modifier.fillMaxSize()) {
            if (state.value.user == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                ) {
                    SnapdexCircularProgressIndicator(modifier = Modifier.size(48.dp))

                    Text(text = stringResource(id = R.string.loading_pokedex))
                }
            } else {
                Box(
                    modifier =
                        Modifier.alpha(if (activeTab.value == ActiveTab.POKEDEX) 1f else 0f)
                            .zIndex(if (activeTab.value == ActiveTab.POKEDEX) 1f else 0f)
                ) {
                    PokedexTabNavigation(
                        paddingValues = adjustedPaddingValues,
                        mainState = stateFlow,
                        shouldShowNavBar = { shouldShowNavBar.value = it },
                    )
                }

                Box(
                    modifier =
                        Modifier.alpha(if (activeTab.value == ActiveTab.STATS) 1f else 0f)
                            .zIndex(if (activeTab.value == ActiveTab.STATS) 1f else 0f)
                ) {
                    StatsTabNavigation(
                        paddingValues = adjustedPaddingValues,
                        mainState = stateFlow,
                        shouldShowNavBar = { shouldShowNavBar.value = it },
                    )
                }

                Box(
                    modifier =
                        Modifier.alpha(if (activeTab.value == ActiveTab.PROFILE) 1f else 0f)
                            .zIndex(if (activeTab.value == ActiveTab.PROFILE) 1f else 0f)
                ) {
                    ProfileTabNavigation(
                        paddingValues = adjustedPaddingValues,
                        mainState = stateFlow,
                        onLoggedOut = { onAction(MainAction.OnLoggedOut) },
                        shouldShowNavBar = { shouldShowNavBar.value = it },
                    )
                }

                SnapdexNavBar(
                    tabs =
                        arrayOf(
                            TabItem(
                                selectedImage = Icons.GridSelected,
                                unselectedImage = Icons.GridUnselected,
                                onClick = { activeTab.value = ActiveTab.POKEDEX },
                            ),
                            TabItem(
                                selectedImage = Icons.StatsSelected,
                                unselectedImage = Icons.StatsUnselected,
                                onClick = { activeTab.value = ActiveTab.STATS },
                            ),
                            TabItem(
                                selectedImage = Icons.ProfileSelected,
                                unselectedImage = Icons.ProfileUnselected,
                                onClick = { activeTab.value = ActiveTab.PROFILE },
                            ),
                        ),
                    selectedTab =
                        when (activeTab.value) {
                            ActiveTab.POKEDEX -> 0
                            ActiveTab.STATS -> 1
                            ActiveTab.PROFILE -> 2
                        },
                    shouldShowNavBar = shouldShowNavBar.value,
                    modifier =
                        Modifier.align(Alignment.BottomCenter)
                            .padding(bottom = paddingValues.calculateBottomPadding() + 8.dp)
                            .zIndex(2f),
                )
            }
        }
    }
}
