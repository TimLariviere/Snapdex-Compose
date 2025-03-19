package com.kanoyatech.snapdex.ui.main.pokemon_detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kanoyatech.snapdex.domain.models.PokemonId
import java.util.Locale

class PokemonDetailViewModel(
    private val pokemonId: PokemonId
): ViewModel() {
    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        Log.d("PokemonDetailsViewModel", "ViewModel initialized with ID: $pokemonId")
    }

    fun setLocale(locale: Locale) {
        //viewModelScope.launch {
        //    val pokemon = dataSource.getPokemonBy(id = pokemonId, locale = locale)!!
        //    val evolutionChain = dataSource.getEvolutionChainFor(pokemonId = pokemonId, locale = locale)!!
        //    state = state.copy(pokemon = pokemon, evolutionChain = evolutionChain)
        //}
    }

    fun onAction(action: PokemonDetailAction) {
        when (action) {
            PokemonDetailAction.OnFavoriteToggleClick ->
                state = state.copy(isFavorite = !state.isFavorite)
            else -> Unit
        }
    }
}