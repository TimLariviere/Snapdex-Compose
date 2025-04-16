package com.kanoyatech.snapdex.ui.main.profile_tab.new_password

sealed interface NewPasswordAction {
    object OnBackClick : NewPasswordAction

    object OnToggleOldPasswordVisibilityClick : NewPasswordAction

    object OnToggleNewPasswordVisibilityClick : NewPasswordAction

    object OnSetPasswordClick : NewPasswordAction
}
