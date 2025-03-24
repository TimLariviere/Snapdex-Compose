package com.kanoyatech.snapdex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.data.repositories.PreferencesRepositoryImpl
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val auth: FirebaseAuth,
    private val preferencesRepositoryImpl: PreferencesRepositoryImpl
): ViewModel() {
    var state by mutableStateOf(MainActivityState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val hasSeenIntro = preferencesRepositoryImpl.getHasSeenIntro()
            val isLoggedIn = auth.currentUser != null
            state = state.copy(
                isLoading = false,
                hasSeenIntro = hasSeenIntro,
                isLoggedIn = isLoggedIn
            )
        }
    }
}