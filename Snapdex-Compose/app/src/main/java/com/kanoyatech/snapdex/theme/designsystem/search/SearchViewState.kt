package com.kanoyatech.snapdex.theme.designsystem.search

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.PokemonType

data class SearchViewState(
    val text: TextFieldState = TextFieldState(),
    val filter: List<PokemonType> = emptyList()
)