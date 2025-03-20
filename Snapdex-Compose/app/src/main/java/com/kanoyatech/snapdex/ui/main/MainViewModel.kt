package com.kanoyatech.snapdex.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    userRepository: UserRepository
): ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        userRepository.getCurrentUser()
            .onEach { user ->
                state = state.copy(user = user)
            }
            .launchIn(viewModelScope)
    }
}