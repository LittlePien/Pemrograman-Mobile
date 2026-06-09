package com.example.moviecatalog.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.ApiResult
import com.example.moviecatalog.data.model.Movie
import com.example.moviecatalog.ui.components.MovieCard
import com.example.moviecatalog.ui.components.MovieCarousel

@Composable
fun HomeScreen(
    moviesResult: ApiResult<List<Movie>>,
    onDetailClick: (Int) -> Unit,
    onBrowserClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onExit: () -> Unit
) {
    Scaffold(
        topBar = { HomeAppBar(onSettingsClick, onExit) }
    ) { innerPadding ->
        when (moviesResult) {
            is ApiResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ApiResult.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(text = "${stringResource(R.string.error_text)} ${moviesResult.message}")
                }
            }
            is ApiResult.Success -> {
                val movies = moviesResult.data
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    item {
                        MovieCarousel(movies = movies, onDetailClick = onDetailClick)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onDetailClick = { onDetailClick(movie.id) },
                            onBrowserClick = { onBrowserClick(movie.tmdbUrl) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(onSettingsClick: () -> Unit, onExit: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.header_title)) },
        navigationIcon = {
            IconButton(onClick = onExit) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit App")
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = stringResource(R.string.settings_button))
            }
        }
    )
}