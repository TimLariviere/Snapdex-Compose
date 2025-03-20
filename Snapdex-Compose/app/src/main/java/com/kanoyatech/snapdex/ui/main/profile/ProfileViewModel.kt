package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    userFlow: Flow<User>,
    private val userRepository: UserRepository
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
        }
    }

    private fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }
}