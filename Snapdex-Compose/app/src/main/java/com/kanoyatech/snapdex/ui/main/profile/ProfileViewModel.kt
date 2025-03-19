package com.kanoyatech.snapdex.ui.main.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val auth: FirebaseAuth
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        //viewModelScope.launch {
        //    val user = userRepository.getCurrentUser().unwrapSuccess()
        //    state = state.copy(
        //        avatarId = user.avatarId,
        //        name = user.name,
        //        email = user.email
        //    )
        //}
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            auth.signOut()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }
}