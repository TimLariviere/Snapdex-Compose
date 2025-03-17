package com.kanoyatech.snapdex.ui.auth.forgot_password

sealed interface ForgotPasswordAction {
    data object OnBackClick: ForgotPasswordAction
    data object OnSendEmailClick: ForgotPasswordAction
    data object OnPopupDismissClick: ForgotPasswordAction
}