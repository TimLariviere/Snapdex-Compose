package com.kanoyatech.snapdex.ui.main.pokedex_tab.components

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.models.PokemonType

data class SearchViewState(
    val text: TextFieldState = TextFieldState(),
    val filter: List<PokemonType> = emptyList()
)