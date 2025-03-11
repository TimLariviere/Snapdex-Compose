package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)
