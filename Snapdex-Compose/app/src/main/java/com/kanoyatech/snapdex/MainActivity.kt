package com.kanoyatech.snapdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                PokemonDetailsScreenRoot(
                    pokemonUi = PokemonUi.fromPokemon(context, Pokemon.Charizard)
                )
            }
        }
    }
}