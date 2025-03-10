package com.kanoyatech.snapdex.ui.pokemon_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.domain.PokemonId
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailsViewModel(
    private val dataSource: DataSource,
    private val pokemonId: PokemonId
): ViewModel() {
    var state by mutableStateOf(PokemonDetailsState())
        private set

    init {
        Log.d("PokemonDetailsViewModel", "ViewModel initialized with ID: $pokemonId")
    }

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            val pokemon = dataSource.getPokemonBy(id = pokemonId, locale = locale)!!
            val evolutionChain = dataSource.getEvolutionChainFor(pokemonId = pokemonId, locale = locale)!!
            state = state.copy(pokemon = pokemon, evolutionChain = evolutionChain)
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