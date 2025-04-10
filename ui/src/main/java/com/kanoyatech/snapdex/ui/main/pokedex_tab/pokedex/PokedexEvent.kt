package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokedex

import com.kanoyatech.snapdex.ui.UiText

sealed interface PokedexEvent {
    data class Error(val error: UiText) : PokedexEvent
}
