package com.kanoyatech.snapdex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.ui.login.LoginScreenRoot
import com.kanoyatech.snapdex.ui.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsScreenRoot
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsViewModel
import com.kanoyatech.snapdex.ui.profile.ProfileScreenRoot
import com.kanoyatech.snapdex.ui.register.RegisterScreenRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable data object LoginRoute
@Serializable data object RegisterRoute
@Serializable data object PokedexRoute
@Serializable data class PokemonDetailsRoute(val pokemonId: PokemonId)
@Serializable data object ProfileRoute

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreenRoot(
                onSuccessfulLogin = {
                    navController.navigate(ProfileRoute)
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
                    navController.navigate(PokedexRoute)
                }
            )
        }

        composable<PokedexRoute> {
            PokedexScreenRoot(
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId = pokemonId))
                }
            )
        }

        composable<PokemonDetailsRoute> { backStackEntry ->
            val route: PokemonDetailsRoute = backStackEntry.toRoute()
            val viewModel: PokemonDetailsViewModel = koinViewModel { parametersOf(route.pokemonId) }
            PokemonDetailsScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId))
                }
            )
        }

        composable<ProfileRoute> {
            ProfileScreenRoot(
                onLoggedOut = {
                    navController.navigate(LoginRoute)
                }
            )
        }
    }
}