package com.example.moviecatalog.navigation

object NavRoutes {
    const val HOME = "home"
    const val DETAIL = "detail/{movieId}"
    const val SETTINGS = "settings"

    fun createDetailRoute(movieId: Int): String {
        return "detail/$movieId"
    }
}