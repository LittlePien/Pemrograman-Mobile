package com.example.moviecatalog.data

import android.content.Context

class LanguagePreference(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun isIndonesian(): Boolean = prefs.getBoolean("is_indonesian", false)

    fun setLanguage(isIndonesian: Boolean) {
        prefs.edit().putBoolean("is_indonesian", isIndonesian).apply()
    }
}