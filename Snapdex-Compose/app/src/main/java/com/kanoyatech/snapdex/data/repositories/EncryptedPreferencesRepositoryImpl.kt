package com.kanoyatech.snapdex.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kanoyatech.snapdex.utils.Crypto
import com.kanoyatech.snapdex.domain.repositories.EncryptedPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class EncryptedPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): EncryptedPreferencesRepository {
    private object PreferencesKeys {
        val OPENAI_API_KEY = stringPreferencesKey("openai_api_key")
    }

    override suspend fun getOpenAIApiKey(): String {
        val encrypted = dataStore.data.first()[PreferencesKeys.OPENAI_API_KEY]
            ?: return ""
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
        dataStore.edit { data ->
            data[PreferencesKeys.OPENAI_API_KEY] = encrypted
        }
    }
}