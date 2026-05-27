package com.example.lazylistproject.ui

import com.example.lazylistproject.model.ItemData

data class MainUIState(
    val itemList: List<ItemData> = emptyList()
)