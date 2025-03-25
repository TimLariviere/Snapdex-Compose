package com.kanoyatech.snapdex

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordScreenRoot
import com.kanoyatech.snapdex.ui.auth.login.LoginScreenRoot
import com.kanoyatech.snapdex.ui.auth.register.RegisterScreenRoot
import com.kanoyatech.snapdex.ui.intro.IntroScreenRoot
import com.kanoyatech.snapdex.ui.main.MainScreenRoot
import com.kanoyatech.snapdex.ui.main.MainState
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
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
@Serializable data object NewNameRoute
@Serializable data object NewPasswordRoute
@Serializable data object CreditsRoute
@Serializable data object PrivacyPolicyRoute
@Serializable data object ChooseAIModelRoute

@Composable
fun TabsNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    mainState: StateFlow<MainState>,
    onPokemonCatch: (PokemonId) -> Unit,
    onLoggedOut: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = PokedexTabRoute
    ) {
        composable<PokedexTabRoute> {
            val pokemonsFlow = mainState.map { it.pokemons }
            val viewModel: PokedexViewModel = koinViewModel { parametersOf(pokemonsFlow) }
            PokedexScreenRoot(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onPokemonClick = { pokemonId ->
                    navController.navigate(PokemonDetailsRoute(pokemonId = pokemonId))
                },
                onPokemonCatch = { pokemonId ->
                    onPokemonCatch(pokemonId)
                }
            )
        }

        composable<StatsTabRoute> {
            StatsScreenRoot(
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