package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.models.PasswordValidationState

data class RegisterState(
    val avatar: Int = -1,
    val name: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
)
