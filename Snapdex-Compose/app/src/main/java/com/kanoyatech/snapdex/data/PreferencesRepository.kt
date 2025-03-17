package com.kanoyatech.snapdex.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val HAS_SEEN_INTRO = booleanPreferencesKey("has_seen_intro")
    }
    
    suspend fun getHasSeenIntro(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.HAS_SEEN_INTRO] ?: false
    }

    suspend fun setHasSeenIntro(value: Boolean) {
        dataStore.edit { data ->
            data[PreferencesKeys.HAS_SEEN_INTRO] = value
        }
    }
}