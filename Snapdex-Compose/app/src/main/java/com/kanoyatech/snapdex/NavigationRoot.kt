package com.kanoyatech.snapdex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.ui.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsScreenRoot
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsViewModel
import kotlinx.serialization.Serializable

@Serializable
object PokedexRoute

@Serializable
data class PokemonDetailsRoute(val pokemonId: PokemonId)

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokedexRoute
    ) {
        composable<PokedexRoute> {
            PokedexScreenRoot(
                viewModel = PokedexViewModel(),
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId = pokemonId))
                }
            )
        }

        composable<PokemonDetailsRoute> { backStackEntry ->
            val route: PokemonDetailsRoute = backStackEntry.toRoute()
            PokemonDetailsScreenRoot(
                viewModel = PokemonDetailsViewModel(pokemonId = route.pokemonId),
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