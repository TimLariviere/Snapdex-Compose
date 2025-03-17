package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.ui.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel(
    private val auth: FirebaseAuth
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            RegisterAction.OnRegisterClick -> register()
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true, canRegister = false)

            try {
                val result = auth.createUserWithEmailAndPassword(
                    state.email.text.toString(),
                    state.password.text.toString()
                ).await()

                eventChannel.send(RegisterEvent.RegistrationSuccess)
            } catch (e: Exception) {
                eventChannel.send(RegisterEvent.Error(UiText.StringResource(id = R.string.register_error)))
            }

            state = state.copy(isRegistering = false)
        }
    }
}