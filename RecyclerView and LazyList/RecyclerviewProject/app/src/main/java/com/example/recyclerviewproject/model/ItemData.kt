package com.example.recyclerviewproject.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ItemData(
    val id: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
    var isSwitchOn: Boolean = false
)