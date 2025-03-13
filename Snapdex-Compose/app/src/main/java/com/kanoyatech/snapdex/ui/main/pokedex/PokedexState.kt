package com.kanoyatech.snapdex.ui.main.pokedex

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.ui.State

data class PokedexState(
    val isCameraGranted: Boolean = false,
    val state: State = State.LOADING,
    val searchText: TextFieldState = TextFieldState(),
    val pokemons: List<Pokemon> = emptyList(),
    val lastCaught: String = ""
)
