package com.example.roomassignment.navigation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomassignment.MainViewModel
import com.example.roomassignment.MainViewModelFactory
import com.example.roomassignment.ui.screen.DetailScreen
import com.example.roomassignment.ui.screen.HomeScreen
import timber.log.Timber

@Composable
fun AppNavGraph() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(application = context.applicationContext as Application)
    )
    val genresWithBooks by viewModel.genresWithBooks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToDetail.collect { bookId ->
            if (bookId != null) {
                Timber.d("Navigating to detail for bookId=$bookId")
                navController.navigate(NavRoutes.detail(bookId))
                viewModel.onDetailNavigated()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.openBrowser.collect { url ->
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
                viewModel.onBrowserOpened()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(route = NavRoutes.HOME) {
            HomeScreen(
                genresWithBooks = genresWithBooks,
                onBookClick = { bookId -> viewModel.onBookClick(bookId) },
                onLinkClick = { url -> viewModel.onLinkClick(url) }
            )
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId")
            val book = bookId?.let { viewModel.findBook(it) }

            if (book == null) return@composable
            DetailScreen(
                book = book,
                genreName = viewModel.findGenreName(book.genreId),
                onLinkClick = { url -> viewModel.onLinkClick(url) },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}