package com.kanoyatech.snapdex.ui.auth.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isLoginIn: Boolean = false,
    val canLogin: Boolean = false
)
