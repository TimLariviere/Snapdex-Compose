package com.kanoyatech.snapdex.ui.main.profile.new_name

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.PasswordValidationState

data class NewNameState(
    val name: TextFieldState = TextFieldState(),
    val isNameValid: Boolean = false,
    val showNameChangedPopup: Boolean = false,
    val canChangeName: Boolean = false,
    val isChangingName: Boolean = false
)