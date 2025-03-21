package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    userFlow: Flow<User>,
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        userFlow
            .onEach {
                state = state.copy(user = it)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
            ProfileAction.OnResetProgressClick -> {
                state = state.copy(showProgressResetDialog = true)
            }
            ProfileAction.OnProgressResetCancel -> {
                state = state.copy(showProgressResetDialog = false)
            }
            ProfileAction.OnProgressResetConfirm -> {
                state = state.copy(showProgressResetDialog = false)
                resetProgress()
            }
            ProfileAction.OnDeleteAccountClick -> {
                state = state.copy(showAccountDeletionDialog = true)
            }
            ProfileAction.OnAccountDeletionCancel -> {
                state = state.copy(showAccountDeletionDialog = false)
            }
            ProfileAction.OnAccountDeletionConfirm -> {
                state = state.copy(showAccountDeletionDialog = false)
                deleteAccount()
            }
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logout()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }

    private fun resetProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.resetForUser(state.user.id!!)
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteCurrentUser()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }
}