package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.datasources.LocalEvolutionChainDataSource
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.usecases.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val pokemonId: PokemonId,
    private val pokemonService: PokemonService,
    private val localEvolutionChains: LocalEvolutionChainDataSource,
) : ViewModel() {
    var state by mutableStateOf(PokemonDetailState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemon = pokemonService.getById(pokemonId)!!
            val evolutionChain = localEvolutionChains.getForPokemon(pokemonId)!!
            state = state.copy(pokemon = pokemon, evolutionChain = evolutionChain)
        }
    }

    fun onAction(action: PokemonDetailAction) {
        when (action) {
            else -> Unit
        }
    }
}
