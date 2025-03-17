package com.kanoyatech.snapdex.ui.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.data.PreferencesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class IntroViewModel(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    var state by mutableStateOf(IntroState())
        private set

    private val eventChannel = Channel<IntroEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: IntroAction) {
        when (action) {
            IntroAction.OnNextClick -> next()
        }
    }

    private fun next() {
        viewModelScope.launch {
            if (state.currentPage < IntroState.TOTAL_PAGE_COUNT - 1) {
                state = state.copy(currentPage = state.currentPage + 1)
                eventChannel.send(IntroEvent.PageChanged(state.currentPage))
            } else {
                preferencesRepository.setHasSeenIntro(true)
                eventChannel.send(IntroEvent.PreferencesUpdated)
            }
        }
    }
}