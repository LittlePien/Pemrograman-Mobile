package com.example.lazylistproject.model

import com.example.lazylistproject.R

object DataSource {
    fun loadItems(): List<ItemData> {
        return listOf(
            ItemData(
                id = 1,
                title = R.string.item_title_1,
                description = R.string.item_description_1,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 2,
                title = R.string.item_title_2,
                description = R.string.item_description_2,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 3,
                title = R.string.item_title_3,
                description = R.string.item_description_3,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 4,
                title = R.string.item_title_4,
                description = R.string.item_description_4,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 5,
                title = R.string.item_title_5,
                description = R.string.item_description_5,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 6,
                title = R.string.item_title_6,
                description = R.string.item_description_6,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 7,
                title = R.string.item_title_7,
                description = R.string.item_description_7,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            ),
            ItemData(
                id = 8,
                title = R.string.item_title_8,
                description = R.string.item_description_8,
                image = R.drawable.ic_launcher_background,
                isSwitchOn = false
            )
        )
    }
}