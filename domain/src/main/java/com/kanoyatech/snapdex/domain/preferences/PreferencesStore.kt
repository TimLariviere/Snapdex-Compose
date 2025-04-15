package com.kanoyatech.snapdex.domain.preferences

import com.kanoyatech.snapdex.domain.models.AIModel

interface PreferencesStore {
    suspend fun getHasSeenIntro(): Boolean

    suspend fun setHasSeenIntro(value: Boolean)

    suspend fun getAIModel(): AIModel

    suspend fun setAIModel(value: AIModel)

    suspend fun getOpenAIApiKey(): String

    suspend fun setOpenAIApiKey(value: String)
}
