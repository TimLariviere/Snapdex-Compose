package com.kanoyatech.snapdex.ui.main.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.MainActivity
import com.kanoyatech.snapdex.domain.DataSource
import com.kanoyatech.snapdex.ui.State
import kotlinx.coroutines.launch
import java.util.Locale

class PokedexViewModel(
    private val dataSource: DataSource
): ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    fun setLocale(locale: Locale) {
        viewModelScope.launch {
            val pokemons = dataSource.getAllPokemons(locale)
            state = state.copy(pokemons = pokemons, state = State.IDLE)
        }
    }

    fun onAction(action: PokedexAction) {

    }
}