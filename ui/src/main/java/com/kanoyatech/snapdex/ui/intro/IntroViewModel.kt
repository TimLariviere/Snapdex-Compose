package com.kanoyatech.snapdex.ui.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
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
            is IntroAction.OnCurrentPageChange -> goToPage(action.page)
            IntroAction.OnNextClick -> next()
        }
    }

    private fun goToPage(page: Int) {
        viewModelScope.launch {
            state = state.copy(currentPage = page)
            eventChannel.send(IntroEvent.PageChanged(state.currentPage))
        }
    }

    private fun next() {
        viewModelScope.launch {
            if (state.currentPage < IntroState.TOTAL_PAGE_COUNT - 1) {
                goToPage(state.currentPage + 1)
            } else {
                // If we are on the last page, remember user saw the intro
                preferencesRepository.setHasSeenIntro(true)
                eventChannel.send(IntroEvent.PreferencesUpdated)
            }
        }
    }

    fun setCurrentPage(currentPage: Int) {
        state = state.copy(currentPage = currentPage)
    }
}