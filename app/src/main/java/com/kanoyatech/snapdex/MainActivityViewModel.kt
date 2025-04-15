package com.kanoyatech.snapdex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.preferences.PreferencesStore
import com.kanoyatech.snapdex.usecases.AuthService
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val authService: AuthService,
    private val preferences: PreferencesStore,
) : ViewModel() {
    var state by mutableStateOf(MainActivityState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val hasSeenIntro = preferences.getHasSeenIntro()
            val isLoggedIn = authService.isLoggedIn()
            state =
                state.copy(isLoading = false, hasSeenIntro = hasSeenIntro, isLoggedIn = isLoggedIn)
        }
    }
}
