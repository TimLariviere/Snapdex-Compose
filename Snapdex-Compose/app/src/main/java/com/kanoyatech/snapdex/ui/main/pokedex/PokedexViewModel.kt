package com.kanoyatech.snapdex.ui.main.pokedex

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.services.PokemonClassifier
import com.kanoyatech.snapdex.ui.State
import com.kanoyatech.snapdex.ui.utils.getLocale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

class PokedexViewModel(
    private val auth: FirebaseAuth,
    private val dataSource: DataSource,
    private val classifier: PokemonClassifier
): ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    fun init(context: Context) {
        viewModelScope.launch {
            val userId = auth.currentUser!!.uid
            val pokemons = dataSource.getCaughtPokemons(userId, context.getLocale())
            state = state.copy(pokemons = pokemons, state = State.IDLE)
        }

        viewModelScope.launch(Dispatchers.IO) {
            classifier.init(context)
        }
    }

    fun onAction(action: PokedexAction) {
        when (action) {
            is PokedexAction.OnPhotoTake -> recognizePokemon(action.bitmap)
            else -> Unit
        }
    }

    private fun catchPokemon(pokemonId: PokemonId) {
        viewModelScope.launch {
            val userId = auth.currentUser!!.uid
            val alreadyCaught = dataSource.hasCaughtPokemon(userId = userId, pokemonId = pokemonId)
            var newPokemons = state.pokemons
            if (!alreadyCaught) {
                dataSource.addPokemonToUser(auth.currentUser!!.uid, pokemonId)
                newPokemons = dataSource.getCaughtPokemons(userId, Locale.ENGLISH)
            }

            val lastCaught = newPokemons.find { it.id == pokemonId }
            state = state.copy(pokemons = newPokemons, lastCaught = lastCaught?.name ?: "Unknown")
        }
    }

    private fun recognizePokemon(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonId = classifier.classify(bitmap)
            if (pokemonId != null) {
                catchPokemon(pokemonId)
            }
        }
    }
}