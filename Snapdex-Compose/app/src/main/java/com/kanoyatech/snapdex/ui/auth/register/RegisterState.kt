package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.PasswordValidationState

data class RegisterState(
    val avatar: Int = -1,
    val isAvatarValid: Boolean = false,
    val showAvatarPicker: Boolean = false,
    val name: TextFieldState = TextFieldState(),
    val isNameValid: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)