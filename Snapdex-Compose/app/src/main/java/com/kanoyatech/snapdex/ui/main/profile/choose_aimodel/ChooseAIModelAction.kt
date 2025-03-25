package com.kanoyatech.snapdex.ui.main.profile.choose_aimodel

import com.kanoyatech.snapdex.domain.AIModel

sealed interface ChooseAIModelAction {
    object OnBackClick: ChooseAIModelAction
    data class OnAIModelSelect(val selected: AIModel): ChooseAIModelAction
    object OnSaveClick: ChooseAIModelAction
}