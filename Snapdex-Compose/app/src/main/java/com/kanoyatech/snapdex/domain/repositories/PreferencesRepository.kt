package com.kanoyatech.snapdex.domain.repositories

import com.kanoyatech.snapdex.domain.AIModel

interface PreferencesRepository {
    suspend fun getHasSeenIntro(): Boolean
    suspend fun setHasSeenIntro(value: Boolean)
    suspend fun getAIModel(): AIModel
    suspend fun setAIModel(value: AIModel)
}