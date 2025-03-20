package com.kanoyatech.snapdex.ui.main.pokedex

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.PokemonClassifier
import com.kanoyatech.snapdex.domain.models.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PokedexViewModel(
    pokemonsFlow: Flow<List<Pokemon>>,
    private val classifier: PokemonClassifier
): ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    private val eventChannel = Channel<PokedexEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        pokemonsFlow
            .onEach { pokemons ->
                state = state.copy(pokemons = pokemons)
            }
            .launchIn(viewModelScope)
    }

    fun init(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            classifier.init(context)
        }
    }

    fun onAction(action: PokedexAction) {
        when (action) {
            is PokedexAction.OnPhotoTake -> recognizePokemon(action.bitmap)
            is PokedexAction.RemoveFilterClick -> {
                state = state.copy(
                    searchState = state.searchState.copy(
                        filter = state.searchState.filter.filter { it != action.type }
                    )
                )
            }
            else -> Unit
        }
    }

    private fun recognizePokemon(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonId = classifier.classify(bitmap)
            if (pokemonId != null) {
                eventChannel.send(PokedexEvent.OnPokemonCatch(pokemonId))
            }
        }
    }
}