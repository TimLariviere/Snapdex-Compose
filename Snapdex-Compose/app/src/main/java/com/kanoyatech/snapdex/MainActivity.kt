package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.components.GifImage
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsScreenRoot
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonUi

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PokemonDetailsScreenRoot(
                    pokemonUi = PokemonUi.Charizard
                )
            }
        }
    }
}