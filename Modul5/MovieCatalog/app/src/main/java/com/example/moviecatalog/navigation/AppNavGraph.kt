package com.example.moviecatalog.navigation

import android.content.Intent
import androidx.activity.ComponentActivity
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
import com.example.moviecatalog.MovieCatalogApplication
import com.example.moviecatalog.MainViewModel
import com.example.moviecatalog.MainViewModelFactory
import com.example.moviecatalog.ui.screen.DetailScreen
import com.example.moviecatalog.ui.screen.HomeScreen
import com.example.moviecatalog.ui.screen.SettingsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as MovieCatalogApplication

    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(application.repository)
    )

    val moviesResult by viewModel.movies.collectAsState()
    val selectedMovie by viewModel.selectedMovie.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(route = NavRoutes.HOME) {
            HomeScreen(
                moviesResult = moviesResult,
                onDetailClick = { movieId -> navController.navigate(NavRoutes.createDetailRoute(movieId)) },
                onBrowserClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                onSettingsClick = { navController.navigate(NavRoutes.SETTINGS) },
                onExit = { (context as? ComponentActivity)?.finish() }
            )
        }

        composable(route = NavRoutes.SETTINGS) {
            SettingsScreen(
                onLanguageChanged = { viewModel.fetchMovies() },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")

            LaunchedEffect(movieId) {
                movieId?.let { viewModel.loadMovieById(it) }
            }

            selectedMovie?.let { movie ->
                DetailScreen(
                    movie = movie,
                    onSettingsClick = { navController.navigate(NavRoutes.SETTINGS) },
                    onBackClick = {
                        viewModel.clearSelectedMovie()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}