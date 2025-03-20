package com.kanoyatech.snapdex.ui.main.pokemon_detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.EvolutionChainRepository
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonDetailViewModel(
    private val pokemonId: PokemonId,
    private val pokemonRepository: PokemonRepository,
    private val evolutionChainRepository: EvolutionChainRepository
): ViewModel() {
    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        Log.d("PokemonDetailsViewModel", "ViewModel initialized with ID: $pokemonId")
    }

    fun initialize(locale: Locale) {
        viewModelScope.launch {
            val pokemon = pokemonRepository.getPokemonById(pokemonId, locale)!!
            val evolutionChain = evolutionChainRepository.getEvolutionChainForPokemon(pokemonId, locale)!!
            state = state.copy(pokemon = pokemon, evolutionChain = evolutionChain)
        }
    }

    fun onAction(action: PokemonDetailAction) {
        when (action) {
            PokemonDetailAction.OnFavoriteToggleClick ->
                state = state.copy(isFavorite = !state.isFavorite)
            else -> Unit
        }
    }
}