package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.theme.designsystem.search.SnapdexSearchViewState

data class PokedexState(
    val isCameraGranted: Boolean = false,
    val searchState: SnapdexSearchViewState = SnapdexSearchViewState(),
    val allPokemons: List<Pokemon> = emptyList(),
    val filteredPokemons: List<Pokemon>? = emptyList(),
    val lastCaught: PokemonCaught? = null
)
