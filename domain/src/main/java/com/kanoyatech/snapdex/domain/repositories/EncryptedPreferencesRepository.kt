package com.kanoyatech.snapdex.domain.repositories

interface EncryptedPreferencesRepository {
    suspend fun getOpenAIApiKey(): String
    suspend fun setOpenAIApiKey(value: String)
}