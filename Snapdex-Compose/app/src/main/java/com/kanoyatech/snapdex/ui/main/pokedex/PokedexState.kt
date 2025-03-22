package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.theme.designsystem.search.SearchViewState

data class PokedexState(
    val isCameraGranted: Boolean = false,
    val searchState: SearchViewState = SearchViewState(),
    val allPokemons: List<Pokemon> = emptyList(),
    val filteredPokemons: List<Pokemon>? = emptyList(),
    val lastCaught: PokemonCaught? = null
)
