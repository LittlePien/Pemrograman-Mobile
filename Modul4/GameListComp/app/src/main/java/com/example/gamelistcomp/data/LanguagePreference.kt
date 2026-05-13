package com.example.gamelistcomp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class LanguagePreference(private val context: Context){
    companion object {
        val IS_INDONESIAN = booleanPreferencesKey("is_indonesian")
    }

    val isIndonesian: Flow<Boolean> = context.dataStore.data.map { prefs -> prefs[IS_INDONESIAN] ?: false }

    suspend fun setLanguage(isIndonesian: Boolean){
        context.dataStore.edit { prefs -> prefs[IS_INDONESIAN] = isIndonesian }
    }
}