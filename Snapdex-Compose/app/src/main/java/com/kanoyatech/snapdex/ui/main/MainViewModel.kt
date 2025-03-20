package com.kanoyatech.snapdex.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialize(locale: Locale) {
        userRepository.getCurrentUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Pair(user, emptyList()))
                } else {
                    pokemonRepository.getPokemonsCaughtByUser(user.id!!, locale)
                        .map {
                            Pair(user, it)
                        }
                }
            }
            .onEach { pair ->
                state = state.copy(
                    user = pair.first,
                    pokemons = pair.second
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: MainAction) {
        when (action) {
            is MainAction.OnPokemonCatch -> catchPokemon(action.pokemonId)
            else -> Unit
        }
    }

    private fun catchPokemon(pokemonId: PokemonId) {
        viewModelScope.launch {
            val userId = state.user?.id
                ?: return@launch

            val result = pokemonRepository.catchPokemon(userId, pokemonId)
            // TODO: handle error
        }
    }
}