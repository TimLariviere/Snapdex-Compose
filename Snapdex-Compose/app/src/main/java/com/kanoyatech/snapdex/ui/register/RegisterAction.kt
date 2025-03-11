package com.kanoyatech.snapdex.ui.register

sealed interface RegisterAction {
    data object OnBackClick: RegisterAction
    data object OnRegisterClick: RegisterAction
    data object OnTogglePasswordVisibility: RegisterAction
}