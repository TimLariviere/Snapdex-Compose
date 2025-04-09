package com.kanoyatech.snapdex.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.SyncRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
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

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
    private val syncRepository: SyncRepository
): ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        userRepository.getCurrentUserFlow()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(Pair(null, emptyList()))
                } else {
                    pokemonRepository.getPokemonsCaughtByUser(user.id!!)
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

        viewModelScope.launch(Dispatchers.IO) {
            syncRepository.syncData()
        }
    }
}