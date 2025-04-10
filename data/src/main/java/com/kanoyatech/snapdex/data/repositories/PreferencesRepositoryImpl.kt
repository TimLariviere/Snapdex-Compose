package com.kanoyatech.snapdex.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.kanoyatech.snapdex.domain.AIModel
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.first

class PreferencesRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    PreferencesRepository {
    private object PreferencesKeys {
        val HAS_SEEN_INTRO = booleanPreferencesKey("has_seen_intro")
        val AI_MODEL = intPreferencesKey("ai_model")
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
}
