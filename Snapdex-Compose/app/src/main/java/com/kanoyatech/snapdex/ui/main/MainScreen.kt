package com.kanoyatech.snapdex.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kanoyatech.snapdex.PokedexTabRoute
import com.kanoyatech.snapdex.ProfileTabRoute
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.StatsTabRoute
import com.kanoyatech.snapdex.TabsNavigation

@Composable
fun MainScreen(
    onLoggedOut: () -> Unit
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavigationBar =
        when (currentDestination?.route) {
            "com.kanoyatech.snapdex.PokemonDetailsRoute/{pokemonId}" -> false
            else -> true
        }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showNavigationBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar {
                    TabItem(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_pokeball),
                        text = stringResource(id = R.string.pokedex),
                        selected = currentDestination?.route == PokedexTabRoute::class.qualifiedName,
                    ) {
                        navController.navigate(PokedexTabRoute) {
                            popUpTo(PokedexTabRoute) {
                                inclusive = true
                            }
                        }
                    }

                    TabItem(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_statistics),
                        text = stringResource(id = R.string.statistics),
                        selected = currentDestination?.route == StatsTabRoute::class.qualifiedName,
                    ) {
                        navController.navigate(StatsTabRoute) {
                            popUpTo(PokedexTabRoute) {
                                inclusive = true
                            }
                        }
                    }

                    TabItem(
                        imageVector = Icons.Filled.AccountCircle,
                        text = stringResource(id = R.string.profile),
                        selected = currentDestination?.route == ProfileTabRoute::class.qualifiedName,
                    ) {
                        navController.navigate(ProfileTabRoute) {
                            popUpTo(PokedexTabRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        TabsNavigation(
            navController = navController,
            paddingValues = paddingValues,
            onLoggedOut = onLoggedOut
        )
    }
}

@Composable
fun RowScope.TabItem(
    imageVector: ImageVector,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
            )
        },
        label = {
            Text(
                text = text
            )
        }
    )
}