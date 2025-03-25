package com.kanoyatech.snapdex.ui.main.profile.choose_aimodel

import androidx.compose.foundation.text.input.TextFieldState
import com.kanoyatech.snapdex.domain.AIModel

data class ChooseAIModelState(
    val selected: AIModel? = null,
    val openAIApiKey: TextFieldState = TextFieldState(),
    val canSave: Boolean = false,
    val isSaving: Boolean = false
)
