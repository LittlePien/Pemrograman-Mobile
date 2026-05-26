package com.example.recyclerviewassignmentxml.ui

import com.example.recyclerviewproject.model.ItemData

data class MainUIState(
    val itemList: List<ItemData> = emptyList()
)