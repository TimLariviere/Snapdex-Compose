package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.repositories.RegisterError
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.domain.UserDataValidator
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.utils.TypedResult
import com.kanoyatech.snapdex.utils.textAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        snapshotFlow { state.avatar }
            .onEach {
                val isAvatarValid = it > -1
                state = state.copy(
                    isAvatarValid = isAvatarValid,
                    canRegister = isAvatarValid && state.isNameValid && state.isEmailValid && state.passwordValidationState.isValid
                )
            }
            .launchIn(viewModelScope)

        state.name.textAsFlow()
            .onEach {
                val isNameValid = userDataValidator.validateName(it.toString())
                state = state.copy(
                    isNameValid = isNameValid,
                    canRegister = state.isAvatarValid && isNameValid && state.isEmailValid && state.passwordValidationState.isValid
                )
            }
            .launchIn(viewModelScope)

        state.email.textAsFlow()
            .onEach {
                val isEmailValid = userDataValidator.validateEmail(it.toString())
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canRegister = state.isAvatarValid && state.isNameValid && isEmailValid && state.passwordValidationState.isValid
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach {
                val passwordValidationState = userDataValidator.validatePassword(it.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isAvatarValid && state.isNameValid && state.isEmailValid && passwordValidationState.isValid
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnAvatarSelectionChange -> {
                state = state.copy(
                    avatar = action.avatar,
                    showAvatarPicker = false
                )
            }
            RegisterAction.OnOpenAvatarPicker -> {
                state = state.copy(showAvatarPicker = true)
            }
            RegisterAction.OnCloseAvatarPicker -> {
                state = state.copy(showAvatarPicker = false)
            }
            RegisterAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            RegisterAction.OnRegisterClick -> register()
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isRegistering = true)

            val result = userRepository.register(
                avatarId = state.avatar,
                name = state.name.text.toString(),
                email = state.email.text.toString(),
                password = state.password.text.toString()
            )

            state = state.copy(isRegistering = false)

            when (result) {
                is TypedResult.Error -> {
                    val message =
                        when (result.error) {
                            is RegisterError.InvalidEmail -> UiText.StringResource(id = R.string.register_invalid_email)
                            is RegisterError.InvalidPassword -> UiText.StringResource(id = R.string.register_invalid_password)
                            is RegisterError.EmailAlreadyUsed -> UiText.StringResource(id = R.string.register_email_already_used)
                            is RegisterError.AccountCreationFailed -> UiText.StringResource(id = R.string.register_failed)
                        }

                    eventChannel.send(RegisterEvent.Error(message))
                }
                is TypedResult.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}