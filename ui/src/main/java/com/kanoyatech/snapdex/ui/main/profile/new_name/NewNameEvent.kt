package com.kanoyatech.snapdex.ui.main.profile.new_name

import com.kanoyatech.snapdex.ui.UiText

sealed interface NewNameEvent {
    data class Error(val error: UiText): NewNameEvent
    object NameChanged: NewNameEvent
}