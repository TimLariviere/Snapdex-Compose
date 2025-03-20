package com.kanoyatech.snapdex.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kanoyatech.snapdex.PokedexTabRoute
import com.kanoyatech.snapdex.ProfileTabRoute
import com.kanoyatech.snapdex.StatsTabRoute
import com.kanoyatech.snapdex.TabsNavigation
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.designsystem.SnapdexNavBar
import com.kanoyatech.snapdex.theme.designsystem.TabItem
import com.kanoyatech.snapdex.ui.utils.getLocale
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreenRoot(
    viewModel: MainViewModel = koinViewModel(),
    onLoggedOut: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.initialize(context.getLocale())
    }

    MainScreen(
        state = viewModel.state,
        onLoggedOut = onLoggedOut
    )
}

@Composable
fun MainScreen(
    state: MainState,
    onLoggedOut: () -> Unit
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold { paddingValues ->
        val adjustedPaddingValues = PaddingValues(
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            top = (paddingValues.calculateTopPadding() - 16.dp).coerceAtLeast(0.dp), // Not sure why Scaffold has a top padding that is too large
            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
            bottom = paddingValues.calculateBottomPadding() + 48.dp
        )

        if (state.user == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TabsNavigation(
                    navController = navController,
                    paddingValues = adjustedPaddingValues,
                    user = state.user,
                    pokemons = state.pokemons,
                    onLoggedOut = onLoggedOut
                )

                SnapdexNavBar(
                    tabs = arrayOf(
                        TabItem(
                            imageVector = Icons.Pokeball,
                            onClick = {
                                navController.navigate(PokedexTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        ),
                        TabItem(
                            imageVector = Icons.Statistics,
                            onClick = {
                                navController.navigate(StatsTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        ),
                        TabItem(
                            imageVector = Icons.Profile,
                            onClick = {
                                navController.navigate(ProfileTabRoute) {
                                    popUpTo(PokedexTabRoute) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    ),
                    shouldShowNavBar = {
                        when (currentDestination?.route) {
                            "com.kanoyatech.snapdex.PokemonDetailsRoute/{pokemonId}" -> false
                            else -> true
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = paddingValues.calculateBottomPadding() + 8.dp)
                )
            }
        }
    }
}