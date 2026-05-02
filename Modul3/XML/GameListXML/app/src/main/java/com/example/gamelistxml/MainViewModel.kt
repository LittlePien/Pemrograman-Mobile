package com.example.gamelistxml

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamelistcomp.data.gameSample
import com.example.gamelistxml.data.LanguagePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val langPref = LanguagePreference(application)
    val game = gameSample

    private val _isIndonesian = MutableStateFlow(false)
    val isIndonesian = _isIndonesian.asStateFlow()

    init {
        viewModelScope.launch {
            langPref.isIndonesian.collectLatest {
                _isIndonesian.value = it
            }
        }
    }

    fun toggleLanguage(isIndonesian: Boolean) {
        viewModelScope.launch {
            langPref.setLanguage(isIndonesian)
        }
    }
}