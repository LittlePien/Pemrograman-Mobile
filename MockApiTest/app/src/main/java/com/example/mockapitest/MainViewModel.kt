package com.example.mockapitest

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = ApiService.fetchData()
            _uiState.value = if (result != null) {
                UiState.Success(result)
            } else {
                UiState.Error("Failed to fetch data")
            }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: ApiResponse) : UiState()
    data class Error(val message: String) : UiState()
}