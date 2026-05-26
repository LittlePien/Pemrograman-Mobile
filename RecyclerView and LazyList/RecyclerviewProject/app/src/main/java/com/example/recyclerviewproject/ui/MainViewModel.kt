package com.example.recyclerviewproject.ui

import androidx.lifecycle.ViewModel
import com.example.recyclerviewassignmentxml.ui.MainUIState
import com.example.recyclerviewproject.model.DataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.update { currentState ->
            currentState.copy(
                itemList = DataSource.loadItems()
            )
        }
    }

    fun updateSwitchState(itemId: Int, isChecked: Boolean) {
        _uiState.update { currentState ->
            val updatedList = currentState.itemList.map { item ->
                if (item.id == itemId) {
                    item.copy(isSwitchOn = isChecked)
                } else {
                    item
                }
            }
            currentState.copy(itemList = updatedList)
        }
    }
}