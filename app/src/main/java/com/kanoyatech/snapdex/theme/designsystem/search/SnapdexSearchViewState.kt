package com.kanoyatech.snapdex.theme.designsystem.search

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.models.PokemonType

data class SnapdexSearchViewState(
    val text: TextFieldState = TextFieldState(),
    val filter: List<PokemonType> = emptyList()
)