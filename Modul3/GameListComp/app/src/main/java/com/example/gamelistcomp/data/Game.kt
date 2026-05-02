package com.example.gamelistcomp.data

import com.example.gamelistcomp.R

data class Game(
    val id: Int,
    val title: Int,
    val descriptionEn: Int,
    val descriptionId: Int,
    val review: Int,
    val ratings: Int,
    val imageRes: Int,
    val detailImageRes: Int,
    val steamLink: String
)

val gameSample = listOf(
    Game(1, R.string.CO33Name, R.string.CO33Desc, R.string.CO33DescId, R.string.CO33Review, R.string.CO33Rate, R.drawable.cover_co33, R.drawable.detailcover2_co33, "https://store.steampowered.com/app/1903340/Clair_Obscur_Expedition_33/"),
    Game(2, R.string.DOS2Name, R.string.DOS2Desc, R.string.DOS2DescId, R.string.DOS2Review, R.string.DOS2Rate, R.drawable.cover_dos2, R.drawable.detailcover_dos2, "https://store.steampowered.com/app/435150/Divinity_Original_Sin_2__Definitive_Edition/"),
    Game(3, R.string.W3Name, R.string.W3Desc, R.string.W3DescId, R.string.W3Review, R.string.W3Rate, R.drawable.cover_w3, R.drawable.detailcover_w3, "https://store.steampowered.com/app/292030/The_Witcher_3_Wild_Hunt/"),
    Game(4, R.string.HadesName, R.string.HadesDesc, R.string.HadesDescId, R.string.HadesReview, R.string.HadesRate, R.drawable.cover_hades, R.drawable.detailcover_hades, "https://store.steampowered.com/app/1145360/Hades/"),
    Game(5, R.string.RPName, R.string.RPDesc, R.string.RPDescId, R.string.RPReview, R.string.RPRate, R.drawable.cover_rp, R.drawable.detailcover_rp, "https://store.steampowered.com/app/1145360/Hades/")
)