package com.kanoyatech.snapdex.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kanoyatech.snapdex.data.utils.Crypto
import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.domain.preferences.PreferencesStore
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalEncodingApi::class)
class DataPreferencesStore(private val dataStore: DataStore<Preferences>) : PreferencesStore {
    private object PreferencesKeys {
        val HAS_SEEN_INTRO = booleanPreferencesKey("has_seen_intro")
        val AI_MODEL = intPreferencesKey("ai_model")
        val OPENAI_API_KEY = stringPreferencesKey("openai_api_key")
    }

    override suspend fun getHasSeenIntro(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.HAS_SEEN_INTRO] == true
    }

    override suspend fun setHasSeenIntro(value: Boolean) {
        dataStore.edit { data -> data[PreferencesKeys.HAS_SEEN_INTRO] = value }
    }

    override suspend fun getAIModel(): AIModel {
        val intValue = dataStore.data.first()[PreferencesKeys.AI_MODEL]
        return when (intValue) {
            0 -> AIModel.EMBEDDED
            1 -> AIModel.OPENAI
            else -> AIModel.EMBEDDED
        }
    }

    override suspend fun setAIModel(value: AIModel) {
        val intValue =
            when (value) {
                AIModel.EMBEDDED -> 0
                AIModel.OPENAI -> 1
            }
        dataStore.edit { data -> data[PreferencesKeys.AI_MODEL] = intValue }
    }

    override suspend fun getOpenAIApiKey(): String {
        val encrypted = dataStore.data.first()[PreferencesKeys.OPENAI_API_KEY] ?: return ""
        val base64Bytes = encrypted.toByteArray()
        val encryptedBytes = Base64.decode(base64Bytes)
        val decryptedBytes = Crypto.decrypt(encryptedBytes)
        val decrypted = String(decryptedBytes)
        return decrypted
    }

    override suspend fun setOpenAIApiKey(value: String) {
        val decryptedBytes = value.toByteArray()
        val encryptedBytes = Crypto.encrypt(decryptedBytes)
        val encrypted = Base64.encode(encryptedBytes)
        dataStore.edit { data -> data[PreferencesKeys.OPENAI_API_KEY] = encrypted }
    }
}
