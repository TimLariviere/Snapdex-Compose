package com.kanoyatech.snapdex

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.designsystem.AppTheme
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject

@Composable
fun ConfigureWindow() {
    val view = LocalView.current
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    if (!view.isInEditMode) {
        LaunchedEffect(isDarkTheme) {
            val activity = context as Activity
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val window = activity.window
            val wic = WindowInsetsControllerCompat(window, view)
            wic.isAppearanceLightStatusBars = !isDarkTheme
        }
    }
}

class MainActivity: ComponentActivity() {
    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isLoading
            }
        }

        enableEdgeToEdge()

        setContent {
            ConfigureWindow()

            AppTheme {
                if (!viewModel.state.isLoading) {
                    val analytics = koinInject<FirebaseAnalytics>()
                    val navHostController = rememberNavController()
                    RootNavigation(
                        analytics = analytics,
                        navController = navHostController,
                        hasSeenIntro = viewModel.state.hasSeenIntro,
                        isLoggedIn = viewModel.state.isLoggedIn
                    )
                }
            }
        }
    }
}