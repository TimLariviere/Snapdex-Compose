package com.kanoyatech.snapdex.ui.profile

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
}