package com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel

import com.kanoyatech.snapdex.domain.models.AIModel

sealed interface ChooseAIModelEvent {
    data class OnSaved(val model: AIModel) : ChooseAIModelEvent
}
