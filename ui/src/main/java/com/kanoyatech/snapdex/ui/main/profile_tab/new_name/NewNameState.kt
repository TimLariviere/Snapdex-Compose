package com.kanoyatech.snapdex.ui.main.profile_tab.new_name

import androidx.compose.foundation.text.input.TextFieldState

data class NewNameState(
    val name: TextFieldState = TextFieldState(),
    val isNameValid: Boolean = false,
    val showNameChangedPopup: Boolean = false,
    val canChangeName: Boolean = false,
    val isChangingName: Boolean = false,
)
