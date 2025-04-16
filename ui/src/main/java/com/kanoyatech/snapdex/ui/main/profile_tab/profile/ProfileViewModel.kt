package com.kanoyatech.snapdex.ui.main.profile_tab.profile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.preferences.PreferencesStore
import com.kanoyatech.snapdex.ui.AppLocaleManager
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.usecases.AuthService
import com.kanoyatech.snapdex.usecases.DeleteCurrentUserError
import com.kanoyatech.snapdex.usecases.PokemonService
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    userFlow: Flow<User>,
    private val authService: AuthService,
    private val pokemonService: PokemonService,
    private val application: Application,
    private val appLocaleManager: AppLocaleManager,
    private val preferences: PreferencesStore,
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    private val eventChannel = Channel<ProfileEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val model = preferences.getAIModel()
            state = state.copy(aiModel = model)
        }

        userFlow.onEach { state = state.copy(user = it) }.launchIn(viewModelScope)
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnResetProgressClick -> openResetProgressDialog()
            ProfileAction.OnProgressResetConfirm -> resetProgress()

            ProfileAction.OnDeleteAccountClick -> openDeleteAccountDialog()
            ProfileAction.OnAccountDeletionConfirm -> deleteAccount()

            ProfileAction.OnChangeLanguageClick -> openLanguageDialog()
            is ProfileAction.OnLanguageChange -> changeLanguage(action.language)

            is ProfileAction.OnAIModelChange -> {
                state = state.copy(aiModel = action.aiModel)
            }

            ProfileAction.OnLogoutClick -> logout()
            else -> Unit
        }
    }

    private fun openResetProgressDialog() {
        viewModelScope.launch { eventChannel.send(ProfileEvent.OpenResetProgressDialog) }
    }

    private fun openDeleteAccountDialog() {
        viewModelScope.launch { eventChannel.send(ProfileEvent.OpenDeleteAccountDialog) }
    }

    private fun openLanguageDialog() {
        viewModelScope.launch { eventChannel.send(ProfileEvent.OpenLanguageDialog) }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authService.logout()
            eventChannel.send(ProfileEvent.LoggedOut)
        }
    }

    private fun resetProgress() {
        viewModelScope.launch(Dispatchers.IO) { pokemonService.resetForUser(state.user.id!!) }
    }

    private fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isDeletingAccount = true)

            val result = authService.deleteCurrentUser()

            state = state.copy(isDeletingAccount = false)

            when (result) {
                is TypedResult.Error -> {
                    val message =
                        when (result.error) {
                            is DeleteCurrentUserError.DeleteFailed ->
                                UiText.StringResource(id = R.string.delete_user_failed)
                        }

                    eventChannel.send(ProfileEvent.Error(message))
                }
                is TypedResult.Success -> {
                    eventChannel.send(ProfileEvent.LoggedOut)
                }
            }
        }
    }

    private fun changeLanguage(locale: Locale) {
        viewModelScope.launch {
            state = state.copy(language = locale)
            appLocaleManager.changeLanguage(application, locale.language)
        }
    }
}
