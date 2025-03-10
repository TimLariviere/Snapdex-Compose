package com.kanoyatech.snapdex.ui.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.MainActivity
import com.kanoyatech.snapdex.ui.State
import kotlinx.coroutines.launch
import java.util.Locale

class PokedexViewModel: ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            val pokemons = MainActivity.dataSource.getAll(locale)
            state = state.copy(pokemons = pokemons, state = State.IDLE)
        }
    }

    fun onAction(action: PokedexAction) {

    }
}