package com.kanoyatech.snapdex.ui.auth.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.domain.TypedResult
import com.kanoyatech.snapdex.domain.UserDataValidator
import com.kanoyatech.snapdex.domain.repositories.SendPasswordResetEmailError
import com.kanoyatech.snapdex.domain.repositories.UserRepository
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.ui.UiText
import com.kanoyatech.snapdex.ui.utils.textAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val userRepository: UserRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {
    var state by mutableStateOf(ForgotPasswordState())
        private set

    private val eventChannel = Channel<ForgotPasswordEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        state.email.textAsFlow()
            .onEach {
                val isEmailValid = userDataValidator.validateEmail(it.toString())
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canSendEmail = isEmailValid
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            ForgotPasswordAction.OnSendEmailClick -> sendPasswordResetEmail()
            ForgotPasswordAction.OnBackClick -> {
                state = state.copy(showEmailSent = false)
            }
            else -> Unit
        }
    }

    private fun sendPasswordResetEmail() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isSendingEmail = true)

            val result =
                userRepository.sendPasswordResetEmail(state.email.text.toString())

            state = state.copy(isSendingEmail = false)

            when (result) {
                is TypedResult.Error -> {
                    val message =
                        when (result.error) {
                            is SendPasswordResetEmailError.NoSuchEmail -> UiText.StringResource(id = R.string.send_password_reset_no_such_email)
                            is SendPasswordResetEmailError.InvalidEmail -> UiText.StringResource(id = R.string.send_password_reset_invalid_email)
                            is SendPasswordResetEmailError.SendFailed -> UiText.StringResource(id = R.string.send_password_reset_failed)
                        }

                    eventChannel.send(ForgotPasswordEvent.Error(message))
                }
                is TypedResult.Success -> {
                    state = state.copy(showEmailSent = true)
                }
            }
        }
    }
}