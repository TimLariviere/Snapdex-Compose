package com.kanoyatech.snapdex.ui.auth.forgot_password

sealed interface ForgotPasswordAction {
    object OnBackClick : ForgotPasswordAction

    object OnSendEmailClick : ForgotPasswordAction

    object OnPopupDismissClick : ForgotPasswordAction
}
