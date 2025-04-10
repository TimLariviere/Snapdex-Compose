package com.kanoyatech.snapdex.ui.main.profile_tab.profile

import com.kanoyatech.snapdex.ui.UiText

sealed interface ProfileEvent {
    data class Error(val error: UiText): ProfileEvent
    object LoggedOut: ProfileEvent
}