package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.kanoyatech.snapdex.data.RoomDataSource
import com.kanoyatech.snapdex.data.SnapdexDatabase
import com.kanoyatech.snapdex.theme.AppTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Init()

            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    val navHostController = rememberNavController()
                    NavigationRoot(
                        navController = navHostController
                    )
                }
            }
        }
    }

    companion object {
        lateinit var database: SnapdexDatabase
        lateinit var dataSource: RoomDataSource

        @Composable
        fun Init() {
            val context = LocalContext.current

            database = Room.databaseBuilder(
                context,
                SnapdexDatabase::class.java,
                "snapdex.db"
            )
                .createFromAsset("snapdex.db")
                .build()

            dataSource = RoomDataSource(database.pokemonDao)
        }
    }
}