package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonType
import com.kanoyatech.snapdex.theme.designsystem.search.SearchViewState
import com.kanoyatech.snapdex.ui.State

data class PokedexState(
    val isCameraGranted: Boolean = false,
    val state: State = State.LOADING,
    val searchState: SearchViewState = SearchViewState(
        filter = listOf(
            PokemonType.FIRE
        )
    ),
    val pokemons: List<Pokemon> = emptyList(),
    val lastCaught: String = ""
)
