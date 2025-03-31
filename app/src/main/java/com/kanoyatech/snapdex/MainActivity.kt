package com.kanoyatech.snapdex

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.theme.AppTheme
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject

@Composable
fun SetStatusBarColor() {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            val wic = WindowInsetsControllerCompat(window, view)
            wic.isAppearanceLightStatusBars = true
        }
    }
}

class MainActivity: ComponentActivity() {
    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isLoading
            }
        }

        setContent {
            SetStatusBarColor()

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