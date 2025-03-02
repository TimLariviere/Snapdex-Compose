package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.ui.pokemon_details.PokemonDetailsScreenRoot
import com.kanoyatech.snapdex.ui.PokemonUi

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    PokemonDetailsScreenRoot(
                        pokemonUi = PokemonUi.fromPokemon(context, Pokemon.Charmeleon)
                    )
                }
            }
        }
    }
}