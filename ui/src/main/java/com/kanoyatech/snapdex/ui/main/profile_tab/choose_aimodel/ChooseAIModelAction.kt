package com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel

import com.kanoyatech.snapdex.domain.AIModel

sealed interface ChooseAIModelAction {
    object OnBackClick: ChooseAIModelAction
    data class OnAIModelSelect(val selected: AIModel): ChooseAIModelAction
    object OnSaveClick: ChooseAIModelAction
}