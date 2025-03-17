package com.kanoyatech.snapdex.ui.auth.register

sealed interface RegisterAction {
    data object OnBackClick: RegisterAction
    data object OnSelectPictureClick: RegisterAction
    data object OnRegisterClick: RegisterAction
    data object OnTogglePasswordVisibility: RegisterAction
}