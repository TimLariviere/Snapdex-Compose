package com.kanoyatech.snapdex.ui.pokemon_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId

class PokemonDetailsViewModel(
    pokemonId: PokemonId
): ViewModel() {
    var state by mutableStateOf(PokemonDetailsState(
        pokemon = Pokemon.find(pokemonId),
        evolution = Evolution.find(pokemonId)
    ))
        private set

    fun onAction(action: PokemonDetailsAction) {
        when (action) {
            PokemonDetailsAction.OnFavoriteToggleClick ->
                state = state.copy(isFavorite = !state.isFavorite)
            else -> Unit
        }
    }
}