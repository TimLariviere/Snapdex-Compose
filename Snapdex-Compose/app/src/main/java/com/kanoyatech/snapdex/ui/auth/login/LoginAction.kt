package com.kanoyatech.snapdex.ui.auth.login

sealed interface LoginAction {
    object OnLoginClick: LoginAction
    object OnRegisterClick: LoginAction
    object OnForgotPasswordClick: LoginAction
    object OnTogglePasswordVisibility : LoginAction
}