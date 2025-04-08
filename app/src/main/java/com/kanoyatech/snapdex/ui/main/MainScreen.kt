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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.PokedexTabRoute
import com.kanoyatech.snapdex.ProfileTabRoute
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.StatsTabRoute
import com.kanoyatech.snapdex.TabsNavigation
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.components.SnapdexCircularProgressIndicator
import com.kanoyatech.snapdex.designsystem.components.SnapdexNavBar
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.TabItem
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreenRoot(
    analytics: FirebaseAnalytics = koinInject(),
    viewModel: MainViewModel = koinViewModel(),
    onLoggedOut: () -> Unit
) {
    MainScreen(
        analytics = analytics,
        stateFlow = viewModel.state,
        onAction = { action ->
            when (action) {
                MainAction.OnLoggedOut -> onLoggedOut()
            }
        }
    )
}

@Composable
fun MainScreen(
    analytics: FirebaseAnalytics,
    stateFlow: StateFlow<MainState>,
    onAction: (MainAction) -> Unit
) {
    val state = stateFlow.collectAsState()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    SnapdexScaffold { paddingValues ->
        val adjustedPaddingValues = remember(paddingValues) {
            PaddingValues(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                top = (paddingValues.calculateTopPadding() - 16.dp).coerceAtLeast(0.dp), // Not sure why Scaffold has a top padding that is too large
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding() + 48.dp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.value.user == null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    SnapdexCircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.loading_pokedex)
                    )
                }
            } else {
                TabsNavigation(
                    analytics = analytics,
                    navController = navController,
                    paddingValues = adjustedPaddingValues,
                    mainState = stateFlow,
                    onLoggedOut = {
                        onAction(MainAction.OnLoggedOut)
                    }
                )

                SnapdexNavBar(
                    tabs = arrayOf(
                        TabItem(
                            selectedImage = Icons.GridSelected,
                            unselectedImage = Icons.GridUnselected,
                            onClick = {
                                navController.navigate(PokedexTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        ),
                        TabItem(
                            selectedImage = Icons.StatsSelected,
                            unselectedImage = Icons.StatsUnselected,
                            onClick = {
                                navController.navigate(StatsTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        ),
                        TabItem(
                            selectedImage = Icons.ProfileSelected,
                            unselectedImage = Icons.ProfileUnselected,
                            onClick = {
                                navController.navigate(ProfileTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    ),
                    selectedTab = when (currentDestination?.route) {
                        PokedexTabRoute::class.qualifiedName -> 0
                        StatsTabRoute::class.qualifiedName -> 1
                        ProfileTabRoute::class.qualifiedName -> 2
                        else -> 0
                    },
                    shouldShowNavBar = when (currentDestination?.route) {
                        PokedexTabRoute::class.qualifiedName -> true
                        StatsTabRoute::class.qualifiedName -> true
                        ProfileTabRoute::class.qualifiedName -> true
                        else -> false
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = paddingValues.calculateBottomPadding() + 8.dp)
                )
            }
        }
    }
}