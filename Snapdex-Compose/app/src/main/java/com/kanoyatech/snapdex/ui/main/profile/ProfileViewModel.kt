package com.kanoyatech.snapdex.ui.main.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.AIModel
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.PokemonRepository
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.ui.AppLocaleManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale

class ProfileViewModel(
    userFlow: Flow<User>,
    private val userRepository: UserRepository,
    private val pokemonRepository: PokemonRepository,
    private val application: Application,
    private val appLocaleManager: AppLocaleManager,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val model = preferencesRepository.getAIModel()
            state = state.copy(aiModel = model)
        }

        userFlow
            .onEach {
                state = state.copy(user = it)
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ProfileAction) {
        when (action) {
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
            is ProfileAction.OnAIModelChange -> {
                state = state.copy(aiModel = action.aiModel)
            }

            ProfileAction.OnChangeLanguageClick -> {
                state = state.copy(showLanguageDialog = true)
            }
            is ProfileAction.OnLanguageChange -> changeLanguage(action.language)
            ProfileAction.OnLanguageDialogDismiss -> {
                state = state.copy(showLanguageDialog = false)
            }

            ProfileAction.OnLogoutClick -> logout()
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

    private fun changeLanguage(locale: Locale) {
        viewModelScope.launch {
            state = state.copy(language = locale, showLanguageDialog = false)
            appLocaleManager.changeLanguage(application, locale.language)
        }
    }
}