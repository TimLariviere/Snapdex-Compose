package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.EvolutionChainRepository
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonId: PokemonId,
    private val pokemonRepository: PokemonRepository,
    private val evolutionChainRepository: EvolutionChainRepository
): ViewModel() {
    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemon = pokemonRepository.getPokemonById(pokemonId)!!
            val evolutionChain = evolutionChainRepository.getEvolutionChainForPokemon(pokemonId)!!
            state = state.copy(pokemon = pokemon, evolutionChain = evolutionChain)
        }
    }

    fun onAction(action: PokemonDetailAction) {
        when (action) {
            else -> Unit
        }
    }
}