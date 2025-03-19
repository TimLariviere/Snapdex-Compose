package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.data.repositories.UserRepository
import com.kanoyatech.snapdex.utils.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Error -> {}
                is Result.Success -> {
                    state = state.copy(
                        avatarId = result.data.avatarId,
                        name = result.data.name,
                        email = result.data.email
                    )
                }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch {
            auth.signOut()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }
}