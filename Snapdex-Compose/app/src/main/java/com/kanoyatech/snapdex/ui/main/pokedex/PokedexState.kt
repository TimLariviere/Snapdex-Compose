package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.ui.State

data class PokedexState(
    val state: State = State.LOADING,
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList()
)
