package com.kanoyatech.snapdex.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.repositories.LoginError
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.utils.TypedResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            LoginAction.OnLoginClick -> login()
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoginIn = true)

            val result = userRepository.login(
                email = state.email.text.toString(),
                password = state.password.text.toString()
            )

            state = state.copy(isLoginIn = false)

            when (result) {
                is TypedResult.Error -> {
                    val message =
                        when (result.error) {
                            is LoginError.UserNotFoundInRemote -> UiText.StringResource(id = R.string.login_failed)
                            is LoginError.UnknownReason -> UiText.StringResource(id = R.string.login_failed)
                        }

                    eventChannel.send(LoginEvent.Error(message))
                }
                is TypedResult.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccessful)
                }
            }
        }
    }
}