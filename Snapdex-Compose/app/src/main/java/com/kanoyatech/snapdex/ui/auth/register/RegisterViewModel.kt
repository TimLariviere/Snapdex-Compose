package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.data.repositories.UserRepository
import com.kanoyatech.snapdex.domain.User
import com.kanoyatech.snapdex.ui.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.avatar },
            snapshotFlow { state.name.text },
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { avatar, name, email, password ->
            avatar > -1
            && name.isNotBlank()
            && email.isNotBlank()
            && password.isNotBlank()
        }
            .onEach {
                state = state.copy(canRegister = it)
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
        viewModelScope.launch {
            state = state.copy(isRegistering = true)

            try {
                val result = userRepository.addUser(
                    User(
                        id = null,
                        avatarId = state.avatar,
                        name = state.name.text.toString(),
                        email = state.email.text.toString(),
                        password = state.password.text.toString()
                    )
                )

                eventChannel.send(RegisterEvent.RegistrationSuccess)
            } catch (e: Exception) {
                eventChannel.send(RegisterEvent.Error(UiText.StringResource(id = R.string.register_error)))
            }

            state = state.copy(isRegistering = false)
        }
    }

    fun setAvatar(avatar: Int) {
        state = state.copy(avatar = avatar)
    }
}