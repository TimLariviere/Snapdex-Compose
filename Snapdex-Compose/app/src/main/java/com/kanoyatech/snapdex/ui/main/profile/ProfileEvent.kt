package com.kanoyatech.snapdex.ui.main.profile

sealed interface ProfileEvent {
    data object LoggedOut: ProfileEvent
}