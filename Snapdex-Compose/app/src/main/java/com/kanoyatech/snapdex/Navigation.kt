package com.kanoyatech.snapdex

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordScreenRoot
import com.kanoyatech.snapdex.ui.auth.login.LoginScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailScreenRoot
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileScreenRoot
import com.kanoyatech.snapdex.ui.auth.register.RegisterScreenRoot
import com.kanoyatech.snapdex.ui.intro.IntroScreenRoot
import com.kanoyatech.snapdex.ui.main.MainScreen
import com.kanoyatech.snapdex.ui.main.MainScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.main.stats.StatsScreenRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable data object IntroRoute

@Serializable data object AuthNavRoute
@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object ForgotPasswordRoute

@Serializable data object MainRoute

@Composable
fun RootNavigation(
    navController: NavHostController,
    hasSeenIntro: Boolean,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = when {
            isLoggedIn -> MainRoute
            hasSeenIntro -> AuthNavRoute
            else -> IntroRoute
        }
    ) {
        composable<IntroRoute> {
            IntroScreenRoot(
                onContinueClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo<IntroRoute> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        navigation<AuthNavRoute>(
            startDestination = LoginRoute
        ) {
            composable<LoginRoute> {
                LoginScreenRoot(
                    onRegisterClick = {
                        navController.navigate(RegisterRoute)
                    },
                    onForgotPasswordClick = {
                        navController.navigate(ForgotPasswordRoute)
                    },
                    onSuccessfulLogin = {
                        navController.navigate(MainRoute) {
                            popUpTo<AuthNavRoute> {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<RegisterRoute> {
                RegisterScreenRoot(
                    viewModel = koinViewModel(),
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onSuccessfulRegistration = {
                        navController.navigate(MainRoute) {
                            popUpTo(AuthNavRoute) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<ForgotPasswordRoute> {
                ForgotPasswordScreenRoot(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable<MainRoute> {
            MainScreenRoot(
                onLoggedOut = {
                    navController.navigate(LoginRoute) {
                        popUpTo(MainRoute) {
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
    user: User,
    pokemons: List<Pokemon>,
    onLoggedOut: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = PokedexTabRoute
    ) {
        composable<PokedexTabRoute> {
            val viewModel: PokedexViewModel = koinViewModel { parametersOf(pokemons) }
            PokedexScreenRoot(
                viewModel = viewModel,
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
            val viewModel: ProfileViewModel = koinViewModel { parametersOf(user) }
            ProfileScreenRoot(
                viewModel = viewModel,
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