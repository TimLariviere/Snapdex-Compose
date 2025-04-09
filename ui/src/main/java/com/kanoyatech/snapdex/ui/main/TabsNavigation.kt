package com.kanoyatech.snapdex.ui.main

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexScreenRoot
import com.kanoyatech.snapdex.ui.main.pokedex.PokedexViewModel
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailScreenRoot
import com.kanoyatech.snapdex.ui.main.pokemon_detail.PokemonDetailViewModel
import com.kanoyatech.snapdex.ui.main.profile.ProfileAction
import com.kanoyatech.snapdex.ui.main.profile.ProfileScreenRoot
import com.kanoyatech.snapdex.ui.main.profile.ProfileViewModel
import com.kanoyatech.snapdex.ui.main.profile.choose_aimodel.ChooseAIModelScreenRoot
import com.kanoyatech.snapdex.ui.main.profile.credits.CreditsScreen
import com.kanoyatech.snapdex.ui.main.profile.new_name.NewNameScreenRoot
import com.kanoyatech.snapdex.ui.main.profile.new_name.NewNameViewModel
import com.kanoyatech.snapdex.ui.main.profile.new_password.NewPasswordScreenRoot
import com.kanoyatech.snapdex.ui.main.profile.privacy_policy.PrivacyPolicyScreen
import com.kanoyatech.snapdex.ui.main.stats.StatsScreenRoot
import com.kanoyatech.snapdex.ui.main.stats.StatsViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable object PokedexTabRoute
@Serializable object StatsTabRoute
@Serializable object ProfileTabRoute
@Serializable data class PokemonDetailsRoute(val pokemonId: PokemonId)
@Serializable object NewNameRoute
@Serializable object NewPasswordRoute
@Serializable object CreditsRoute
@Serializable object PrivacyPolicyRoute
@Serializable object ChooseAIModelRoute

@Composable
fun TabsNavigation(
    analytics: FirebaseAnalytics,
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainState: StateFlow<MainState>,
    onLoggedOut: () -> Unit
) {
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, params ->
            analytics.logEvent("tabs_navigation", Bundle().apply {
                putString("page_name", destination.route ?: "unknown")
                if (params != null) {
                    putAll(params)
                }
            })
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    NavHost(
        navController = navController,
        startDestination = PokedexTabRoute
    ) {
        composable<PokedexTabRoute> {
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

        composable<StatsTabRoute> {
            val userFlow = mainState.map { it.user }.filterNotNull()
            val viewModel: StatsViewModel = koinViewModel { parametersOf(userFlow) }
            StatsScreenRoot(
                viewModel = viewModel,
                paddingValues = paddingValues
            )
        }

        composable<ProfileTabRoute> {
            val userFlow = mainState.map { it.user }.filterNotNull()
            val viewModel: ProfileViewModel = koinViewModel { parametersOf(userFlow) }
            ProfileScreenRoot(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onLoggedOut = { onLoggedOut() },
                onChangeNameClick = {
                    navController.navigate(NewNameRoute)
                },
                onChangePasswordClick = {
                    navController.navigate(NewPasswordRoute)
                },
                onChangeAIModelClick = {
                    navController.navigate(ChooseAIModelRoute)
                },
                onCreditsClick = {
                    navController.navigate(CreditsRoute)
                },
                onPrivacyPolicyClick = {
                    navController.navigate(PrivacyPolicyRoute)
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

        composable<NewNameRoute> {
            val userFlow = mainState.map { it.user }.filterNotNull()
            val viewModel: NewNameViewModel = koinViewModel { parametersOf(userFlow) }
            NewNameScreenRoot(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<NewPasswordRoute> {
            NewPasswordScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<ChooseAIModelRoute> { navBackStackEntry ->
            val userFlow = mainState.map { it.user }.filterNotNull()
            val profileViewModel: ProfileViewModel = koinViewModel(viewModelStoreOwner = navBackStackEntry) { parametersOf(userFlow) }
            ChooseAIModelScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaved = { model ->
                    profileViewModel.onAction(ProfileAction.OnAIModelChange(model))
                    navController.popBackStack()
                }
            )
        }

        composable<CreditsRoute> {
            CreditsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<PrivacyPolicyRoute> {
            PrivacyPolicyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}