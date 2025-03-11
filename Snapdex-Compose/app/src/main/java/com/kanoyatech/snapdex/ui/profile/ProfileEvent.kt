package com.kanoyatech.snapdex.ui.profile

sealed interface ProfileEvent {
    data object LoggedOut: ProfileEvent
}