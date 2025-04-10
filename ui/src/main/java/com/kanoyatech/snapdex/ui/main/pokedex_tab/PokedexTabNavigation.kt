package com.kanoyatech.snapdex.ui.main.pokedex_tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.ui.main.MainState
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.PokemonDetailScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.PokemonDetailViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable data object PokedexRoute
@Serializable data class PokemonDetailsRoute(val pokemonId: PokemonId)

@Composable
fun PokedexTabNavigation(
    paddingValues: PaddingValues,
    mainState: StateFlow<MainState>,
    shouldShowNavBar: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PokedexRoute
    ) {
        composable<PokedexRoute> {
            shouldShowNavBar(true)

            val userFlow = mainState.map { it.user }.filterNotNull()
            val pokemonsFlow = mainState.map { it.pokemons }
            val viewModel: PokedexViewModel = koinViewModel { parametersOf(userFlow, pokemonsFlow) }
            PokedexScreenRoot(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId = pokemonId))
                }
            )
        }

        composable<PokemonDetailsRoute> { backStackEntry ->
            shouldShowNavBar(false)

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