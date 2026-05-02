package com.example.gamelistcomp.navigation

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
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
import com.example.gamelistcomp.ui.screen.DetailScreen
import com.example.gamelistcomp.ui.screen.SettingsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()
    val isIndonesian by viewModel.isIndonesian.collectAsState()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(route = NavRoutes.HOME) {
            HomeScreen(
                games = viewModel.game,
                isIndonesian = isIndonesian,
                onDetailClick = { gameId -> navController.navigate("detail/$gameId")},
                onBrowserClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
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
            val game = viewModel.game.find { it.id == gameId }

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