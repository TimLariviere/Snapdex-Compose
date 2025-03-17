package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.theme.AppTheme
import org.koin.android.ext.android.inject

class MainActivity: ComponentActivity() {
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navHostController = rememberNavController()
                    RootNavigation(
                        navController = navHostController,
                        hasSeenIntro = false,
                        isLoggedIn = false //auth.currentUser != null
                    )
                }
            }
        }
    }
}