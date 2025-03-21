package com.kanoyatech.snapdex.ui.main.profile.new_password

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.PasswordValidationState

data class NewPasswordState(
    val oldPassword: TextFieldState = TextFieldState(),
    val isOldPasswordVisible: Boolean = false,
    val isOldPasswordValid: Boolean = false,
    val newPassword: TextFieldState = TextFieldState(),
    val isNewPasswordVisible: Boolean = false,
    val newPasswordValidationState: PasswordValidationState = PasswordValidationState(),
    val showPasswordChangedPopup: Boolean = false,
    val canChangePassword: Boolean = false,
    val isChangingPassword: Boolean = false
)