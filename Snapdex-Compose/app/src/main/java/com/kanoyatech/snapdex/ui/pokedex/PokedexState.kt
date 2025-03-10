package com.kanoyatech.snapdex.ui.pokedex

import com.kanoyatech.snapdex.domain.Pokemon

enum class State {
    LOADING,
    IDLE
}

data class PokedexState(
    val state: State = State.LOADING,
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList()
)
