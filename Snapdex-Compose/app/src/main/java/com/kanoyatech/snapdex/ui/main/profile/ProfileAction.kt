package com.kanoyatech.snapdex.ui.main.profile

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
}