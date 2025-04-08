package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.ui.main.pokedex.components.PokemonCaught
import com.kanoyatech.snapdex.ui.main.pokedex.components.SearchViewState

data class PokedexState(
    val user: User? = null,
    val isCameraGranted: Boolean = false,
    val searchState: SearchViewState = SearchViewState(),
    val allPokemons: List<Pokemon> = emptyList(),
    val filteredPokemons: List<Pokemon>? = emptyList(),
    val showRecognitionOverlay: Boolean = false,
    val isRecognizing: Boolean = false,
    val lastCaught: PokemonCaught? = null
)
