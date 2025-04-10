package com.kanoyatech.snapdex.ui.auth.forgot_password

import androidx.compose.foundation.text.input.TextFieldState

data class ForgotPasswordState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val showEmailSent: Boolean = false,
    val isSendingEmail: Boolean = false,
    val canSendEmail: Boolean = false,
)
