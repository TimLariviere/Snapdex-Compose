package com.kanoyatech.snapdex.ui.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PokedexViewModel: ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    fun onAction(action: PokedexAction) {

    }
}