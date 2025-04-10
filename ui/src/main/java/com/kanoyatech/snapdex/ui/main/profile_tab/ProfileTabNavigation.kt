package com.kanoyatech.snapdex.ui.main.profile_tab

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kanoyatech.snapdex.ui.main.MainState
import com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel.ChooseAIModelScreenRoot
import com.kanoyatech.snapdex.ui.main.profile_tab.credits.CreditsScreen
import com.kanoyatech.snapdex.ui.main.profile_tab.new_name.NewNameScreenRoot
import com.kanoyatech.snapdex.ui.main.profile_tab.new_name.NewNameViewModel
import com.kanoyatech.snapdex.ui.main.profile_tab.new_password.NewPasswordScreenRoot
import com.kanoyatech.snapdex.ui.main.profile_tab.privacy_policy.PrivacyPolicyScreen
import com.kanoyatech.snapdex.ui.main.profile_tab.profile.ProfileAction
import com.kanoyatech.snapdex.ui.main.profile_tab.profile.ProfileScreenRoot
import com.kanoyatech.snapdex.ui.main.profile_tab.profile.ProfileViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable object ProfileRoute
@Serializable object NewNameRoute
@Serializable object NewPasswordRoute
@Serializable object CreditsRoute
@Serializable object PrivacyPolicyRoute
@Serializable object ChooseAIModelRoute

@Composable
fun ProfileTabNavigation(
    paddingValues: PaddingValues,
    mainState: StateFlow<MainState>,
    shouldShowNavBar: (Boolean) -> Unit,
    onLoggedOut: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProfileRoute
    ) {
        composable<ProfileRoute> {
            shouldShowNavBar(true)

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

        composable<NewNameRoute> {
            shouldShowNavBar(false)

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
            shouldShowNavBar(false)

            NewPasswordScreenRoot(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<ChooseAIModelRoute> { navBackStackEntry ->
            shouldShowNavBar(false)

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
            shouldShowNavBar(false)

            CreditsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<PrivacyPolicyRoute> {
            shouldShowNavBar(false)

            PrivacyPolicyScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}