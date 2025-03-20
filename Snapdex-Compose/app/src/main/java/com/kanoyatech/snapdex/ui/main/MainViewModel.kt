package com.kanoyatech.snapdex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class MainViewModel(
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialize(locale: Locale) {
        userRepository.getCurrentUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Pair(null, emptyList()))
                } else {
                    pokemonRepository.getPokemonsCaughtByUser(user.id!!, locale)
                        .map {
                            Pair(user, it)
                        }
                }
            }
            .onEach { pair ->
                _state.update {
                    it.copy(
                        user = pair.first,
                        pokemons = pair.second
                    )
                }
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
            val userId = _state.value.user?.id
                ?: return@launch

            val result = pokemonRepository.catchPokemon(userId, pokemonId)
            // TODO: handle error
        }
    }
}