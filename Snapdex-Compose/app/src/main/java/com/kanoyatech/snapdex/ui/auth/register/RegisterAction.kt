package com.kanoyatech.snapdex.ui.auth.register

sealed interface RegisterAction {
    data object OnBackClick: RegisterAction
    data object OnOpenAvatarPicker: RegisterAction
    data object OnCloseAvatarPicker : RegisterAction
    data class OnAvatarSelectionChange(val avatar: Int) : RegisterAction
    data object OnRegisterClick: RegisterAction
    data object OnTogglePasswordVisibility: RegisterAction
}