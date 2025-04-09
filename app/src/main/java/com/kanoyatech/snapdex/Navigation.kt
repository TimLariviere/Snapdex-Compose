package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.ui.auth.forgot_password.ForgotPasswordScreenRoot
import com.kanoyatech.snapdex.ui.auth.login.LoginScreenRoot
import com.kanoyatech.snapdex.ui.auth.register.RegisterScreenRoot
import com.kanoyatech.snapdex.ui.intro.IntroScreenRoot
import com.kanoyatech.snapdex.ui.main.MainScreenRoot
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable object IntroRoute

@Serializable object AuthNavRoute
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object ForgotPasswordRoute

@Serializable object MainRoute

@Composable
fun RootNavigation(
    analytics: FirebaseAnalytics,
    navController: NavHostController,
    hasSeenIntro: Boolean,
    isLoggedIn: Boolean
) {
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, params ->
            analytics.logEvent("root_navigation", Bundle().apply {
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