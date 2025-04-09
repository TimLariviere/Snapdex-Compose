package com.kanoyatech.snapdex.ui.main.profile.new_password

import com.kanoyatech.snapdex.ui.UiText

sealed interface NewPasswordEvent {
    data class Error(val error: UiText): NewPasswordEvent
    object PasswordChanged: NewPasswordEvent
}