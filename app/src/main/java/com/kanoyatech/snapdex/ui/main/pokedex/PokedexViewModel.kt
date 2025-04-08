package com.kanoyatech.snapdex.ui.main.pokedex

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.data.classifiers.Classifier
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.CatchPokemonError
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.ui.main.pokedex.components.PokemonCaught
import com.kanoyatech.snapdex.utils.textAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(FlowPreview::class)
class PokedexViewModel(
    userFlow: Flow<User>,
    pokemonsFlow: Flow<List<Pokemon>>,
    private val classifier: Classifier,
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var state by mutableStateOf(PokedexState())
        private set

    var locale: Locale by mutableStateOf(Locale.ENGLISH)

    private val eventChannel = Channel<PokedexEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val searchFlow = state.searchState.text.textAsFlow()
            .debounce(300L)

        val pokemonsFlow = pokemonsFlow
            .onEach { pokemons ->
                state = state.copy(allPokemons = pokemons)
            }

        val localeFlow = snapshotFlow { locale }

        combine(searchFlow, pokemonsFlow, localeFlow) { searchText, allPokemons, locale ->
            if (searchText.isBlank()) {
                null
            } else {
                allPokemons.filter {
                    val name = it.name.getOrElse(locale) { "" }
                    name.contains(searchText, ignoreCase = true)
                }
            }
        }
            .onEach { filteredPokemons ->
                state = state.copy(filteredPokemons = filteredPokemons)
            }
            .launchIn(viewModelScope)

        userFlow
            .onEach {
                state = state.copy(user = it)
            }
            .launchIn(viewModelScope)
    }

    fun initialize(context: Context) {
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
            PokedexAction.OnRecognitionOverlayDismiss -> {
                state = state.copy(lastCaught = null, showRecognitionOverlay = false)
            }
            else -> Unit
        }
    }

    private fun recognizePokemon(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = state.user?.id ?: return@launch

            state = state.copy(
                showRecognitionOverlay = true,
                isRecognizing = true,
                lastCaught = null
            )

            var lastCaught: PokemonCaught? = null
            val pokemonId = classifier.classify(bitmap)
            if (pokemonId != null) {
                val result = pokemonRepository.catchPokemon(userId, pokemonId)

                when (result) {
                    is TypedResult.Error -> {
                        val message =
                            when (result.error) {
                                is CatchPokemonError.CatchFailed -> UiText.StringResource(id = R.string.catch_failed)
                            }
                        eventChannel.send(PokedexEvent.Error(message))
                    }
                    is TypedResult.Success -> {
                        val pokemon = pokemonRepository.getPokemonById(pokemonId)!!
                        lastCaught = PokemonCaught(
                            id = pokemon.id,
                            name = pokemon.name
                        )
                    }
                }
            }

            state = state.copy(
                isRecognizing = false,
                lastCaught = lastCaught
            )
        }
    }
}