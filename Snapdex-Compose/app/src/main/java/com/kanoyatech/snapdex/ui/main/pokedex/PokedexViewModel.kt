package com.kanoyatech.snapdex.ui.main.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.MainActivity
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.ui.State
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.random.Random

class PokedexViewModel(
    private val auth: FirebaseAuth,
    private val dataSource: DataSource
): ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            val userId = auth.currentUser!!.uid
            val pokemons = dataSource.getCaughtPokemons(userId, locale)
            state = state.copy(pokemons = pokemons, state = State.IDLE)
        }
    }

    fun onAction(action: PokedexAction) {
        when (action) {
            PokedexAction.OnPokemonCatch -> catchPokemon()
            else -> Unit
        }
    }

    private fun catchPokemon() {
        viewModelScope.launch {
            val pokemonId = Random.nextInt(1, 151)
            val userId = auth.currentUser!!.uid
            val alreadyCaught = dataSource.hasCaughtPokemon(userId = userId, pokemonId = pokemonId)
            if (!alreadyCaught) {
                dataSource.addPokemonToUser(auth.currentUser!!.uid, pokemonId)
                state = state.copy(pokemons = dataSource.getCaughtPokemons(userId, Locale.ENGLISH))
            }
        }
    }
}