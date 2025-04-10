package com.kanoyatech.snapdex.ui.main.profile_tab.choose_aimodel

import com.kanoyatech.snapdex.domain.AIModel

sealed interface ChooseAIModelEvent {
    data class OnSaved(val model: AIModel) : ChooseAIModelEvent
}
