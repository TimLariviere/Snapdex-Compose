package com.kanoyatech.snapdex.ui.login

sealed interface LoginAction {
    data object OnLoginClick: LoginAction
    data object OnRegisterClick: LoginAction
    data object OnForgotPasswordClick: LoginAction
    data object OnTogglePasswordVisibility : LoginAction
}