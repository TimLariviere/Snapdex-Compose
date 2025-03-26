package com.kanoyatech.snapdex.ui.auth.login

import com.kanoyatech.snapdex.ui.UiText

sealed interface LoginEvent {
    object LoginSuccessful: LoginEvent
    data class Error(val error: UiText): LoginEvent
}