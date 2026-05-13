package com.example.gamelistcomp.navigation

import android.app.Application
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamelistcomp.MainViewModel
import com.example.gamelistcomp.ui.screen.HomeScreen
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gamelistcomp.MainViewModelFactory
import com.example.gamelistcomp.R
import com.example.gamelistcomp.ui.screen.DetailScreen
import com.example.gamelistcomp.ui.screen.SettingsScreen
import timber.log.Timber

@Composable
fun AppNavGraph() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(
            application = context.applicationContext as Application,
            appTitle = context.getString(R.string.header_titleEn)
        )
    )
    val isIndonesian by viewModel.isIndonesian.collectAsState()
    val gameList by viewModel.gameList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToDetail.collect { gameId ->
            if (gameId != null) {
                val game = gameList.find { it.id == gameId }
                if (game != null) {
                    Timber.d("Data game dipilih = ${context.getString(game.title)}")
                }

                navController.navigate("detail/$gameId")
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
                games = gameList,
                isIndonesian = isIndonesian,
                onDetailClick = { gameId -> viewModel.onDetailClick(gameId) },
                onBrowserClick = { url -> viewModel.onBrowserClick(url) },
                onSettingsClick = {navController.navigate(NavRoutes.SETTINGS)},
                onExit = {(context as? ComponentActivity)?.finish()}
           )
        }

        composable(route = NavRoutes.SETTINGS) {
            SettingsScreen(
                isIndonesian = isIndonesian,
                onLanguageChange = {viewModel.toggleLanguage(it)},
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(
            route = NavRoutes.DETAIL,
            arguments = listOf(navArgument("gameId") {type = NavType.IntType})
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getInt("gameId")
            val game = gameList.find { it.id == gameId }

            if (game == null) return@composable
            DetailScreen(
                game = game,
                isIndonesian = isIndonesian,
                onSettingsClick = {navController.navigate(NavRoutes.SETTINGS)},
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}