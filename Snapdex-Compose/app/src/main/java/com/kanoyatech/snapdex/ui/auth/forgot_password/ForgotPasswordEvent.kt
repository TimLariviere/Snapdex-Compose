package com.kanoyatech.snapdex.ui.auth.forgot_password

import com.kanoyatech.snapdex.ui.UiText

sealed interface ForgotPasswordEvent {
    data class Error(val error: UiText): ForgotPasswordEvent
}