package com.kanoyatech.snapdex.ui.auth.register

sealed interface RegisterAction {
    object OnBackClick : RegisterAction

    object OnOpenAvatarPicker : RegisterAction

    data class OnAvatarSelectionChange(val avatar: Int) : RegisterAction

    object OnRegisterClick : RegisterAction

    object OnTogglePasswordVisibility : RegisterAction
}
