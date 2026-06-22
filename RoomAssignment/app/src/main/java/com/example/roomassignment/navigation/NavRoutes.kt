package com.example.roomassignment.navigation

object NavRoutes {
    const val HOME = "home"
    const val DETAIL = "detail/{bookId}"

    fun detail(bookId: Int) = "detail/$bookId"
}