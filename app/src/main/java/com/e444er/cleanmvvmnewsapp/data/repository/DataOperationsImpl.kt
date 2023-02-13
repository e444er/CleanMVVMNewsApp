package com.e444er.cleanmvvmnewsapp.data.repository

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.e444er.cleanmvvmnewsapp.data.utils.Constants
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.LOCALE_KEY
import com.e444er.cleanmvvmnewsapp.data.utils.Constants.Companion.UI_MODE_KEY
import com.e444er.cleanmvvmnewsapp.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations {

    private object PreferencesKey {
        val localeKey = stringPreferencesKey(LOCALE_KEY)
        val uiModeKey = intPreferencesKey(UI_MODE_KEY)
    }

    override suspend fun updateCurrentLanguageCode(languageTag: String) {
        dataStore.edit {
            it[PreferencesKey.localeKey] = languageTag
        }
    }

    override fun getLanguageCode(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val locale = it[PreferencesKey.localeKey] ?: Constants.DEFAULT_REGION
                locale
            }
    }

    override suspend fun updateUIMode(uiMode: Int) {
        dataStore.edit {
            it[PreferencesKey.uiModeKey] = uiMode
        }
    }

    override fun getUIMode(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                val uiMode = it[PreferencesKey.uiModeKey] ?: AppCompatDelegate.getDefaultNightMode()
                uiMode
            }
    }
}