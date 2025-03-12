package com.kanoyatech.snapdex

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.ui.auth.login.LoginScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailScreenRoot
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileScreenRoot
import com.kanoyatech.snapdex.ui.auth.register.RegisterScreenRoot
import com.kanoyatech.snapdex.ui.main.MainScreen
import com.kanoyatech.snapdex.ui.main.stats.StatsScreenRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object MainRoute

@Composable
fun RootNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) MainRoute else LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreenRoot(
                onSuccessfulLogin = {
                    navController.navigate(MainRoute)
                },
                onRegisterClick = {
                    navController.navigate(RegisterRoute)
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onSuccessfulRegistration = {
                    navController.navigate(MainRoute) {
                        popUpTo(LoginRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<MainRoute> {
            MainScreen(
                onLoggedOut = {
                    navController.navigate(LoginRoute) {
                        popUpTo(LoginRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Serializable data object PokedexTabRoute
@Serializable data object StatsTabRoute
@Serializable data object ProfileTabRoute
@Serializable data class PokemonDetailsRoute(val pokemonId: PokemonId)

@Composable
fun TabsNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onLoggedOut: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = PokedexTabRoute
    ) {
        composable<PokedexTabRoute> {
            PokedexScreenRoot(
                paddingValues = paddingValues,
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId = pokemonId))
                }
            )
        }

        composable<StatsTabRoute> {
            StatsScreenRoot(
                paddingValues = paddingValues
            )
        }

        composable<ProfileTabRoute> {
            ProfileScreenRoot(
                paddingValues = paddingValues,
                onLoggedOut = {
                    onLoggedOut()
                }
            )
        }

        composable<PokemonDetailsRoute> { backStackEntry ->
            val route: PokemonDetailsRoute = backStackEntry.toRoute()
            val viewModel: PokemonDetailViewModel = koinViewModel { parametersOf(route.pokemonId) }
            PokemonDetailScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId))
                }
            )
        }
    }
}