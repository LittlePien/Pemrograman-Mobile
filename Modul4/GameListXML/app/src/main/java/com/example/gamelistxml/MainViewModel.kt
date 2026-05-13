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
import timber.log.Timber

class MainViewModel(
    application: Application,
    val appTitle: String
) : AndroidViewModel(application) {
    private val langPref = LanguagePreference(application)

    private val _gameList = MutableStateFlow(gameSample)
    val gameList = _gameList.asStateFlow()

    private val _isIndonesian = MutableStateFlow(false)
    val isIndonesian = _isIndonesian.asStateFlow()

    private val _navigateToDetail = MutableStateFlow<Int?>(null)
    val navigateToDetail = _navigateToDetail.asStateFlow()

    private val _openBrowser = MutableStateFlow<String?>(null)
    val openBrwser = _navigateToDetail.asStateFlow()

    init {
        Timber.d("Judul Aplikasi: $appTitle")
        Timber.d("List game telah diisi ${_gameList.value.size} item")
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

    fun onDetailClick(gameId: Int){
        Timber.d("Tombol detail ditekan untuk game Id = $gameId")
        viewModelScope.launch {
            _navigateToDetail.value = gameId
        }
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun onBrowserClick(url: String){
        Timber.d("Tombol telah ditekan untuk url = $url")
        viewModelScope.launch {
            _openBrowser.value = url
        }
    }

    fun onBrowserOpened() {
        _openBrowser.value = null
    }
}