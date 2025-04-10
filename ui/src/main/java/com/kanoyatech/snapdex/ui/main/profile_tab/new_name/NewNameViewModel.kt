package com.kanoyatech.snapdex.ui.main.profile_tab.new_name

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.domain.UserDataValidator
import com.kanoyatech.snapdex.domain.models.User
import com.kanoyatech.snapdex.domain.repositories.ChangeNameError
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.ui.utils.textAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewNameViewModel(
    userFlow: Flow<User>,
    private val userDataValidator: UserDataValidator,
    private val userRepository: UserRepository
): ViewModel() {
    var state by mutableStateOf(NewNameState())
        private set

    private val eventChannel = Channel<NewNameEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        userFlow
            .onEach {
                state = state.copy(name = TextFieldState(it.name))
            }
            .launchIn(viewModelScope)

        state.name.textAsFlow()
            .onEach {
                val isNameValid = userDataValidator.validateName(it.toString())
                state = state.copy(
                    isNameValid = isNameValid,
                    canChangeName = isNameValid
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: NewNameAction) {
        when (action) {
            NewNameAction.OnSetNameClick -> setName()
            NewNameAction.OnNameChangedPopupDismiss -> {
                state = state.copy(showNameChangedPopup = false)
            }
            else -> Unit
        }
    }

    private fun setName() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isChangingName = true)

            val result = userRepository.changeName(state.name.text.toString())

            state = state.copy(isChangingName = false)

            when (result) {
                is TypedResult.Error -> {
                    val message =
                        when (result.error) {
                            is ChangeNameError.ChangeFailed -> UiText.StringResource(R.string.could_not_change_name)
                        }

                    eventChannel.send(NewNameEvent.Error(message))
                }
                is TypedResult.Success -> {
                    eventChannel.send(NewNameEvent.NameChanged)
                    state = state.copy(showNameChangedPopup = true)
                }
            }
        }
    }
}