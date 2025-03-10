package com.kanoyatech.snapdex.ui.pokemon_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.MainActivity
import com.kanoyatech.snapdex.domain.PokemonId
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailsViewModel(
    pokemonId: PokemonId
): ViewModel() {
    var state by mutableStateOf(PokemonDetailsState(pokemonId = pokemonId))
        private set

    init {
        Log.d("PokemonDetailsViewModel", "ViewModel initialized with ID: $pokemonId")
    }

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            val pokemon = MainActivity.dataSource.getBy(id = state.pokemonId, locale = locale)!!
            state = state.copy(pokemon = pokemon)
        }
    }

    fun onAction(action: PokemonDetailsAction) {
        when (action) {
            PokemonDetailsAction.OnFavoriteToggleClick ->
                state = state.copy(isFavorite = !state.isFavorite)
            else -> Unit
        }
    }
}