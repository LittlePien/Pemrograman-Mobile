package com.example.gamelistcomp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamelistcomp.R
import com.example.gamelistcomp.data.Game
import com.example.gamelistcomp.data.gameSample
import com.example.gamelistcomp.ui.components.GameCard
import com.example.gamelistcomp.ui.components.GameCarousel

@Composable
fun HomeScreen(
    games: List<Game>,
    isIndonesian: Boolean,
    onDetailClick: (Int) -> Unit,
    onBrowserClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onExit: () -> Unit,
) {
    Scaffold(
        topBar = {
            HomeAppBar(
                onSettingsClick = onSettingsClick,
                onExit = onExit,
                isIndonesian = isIndonesian,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                GameCarousel(
                    games = games,
                    onDetailClick = onDetailClick
                )
            }

            items(games) { game ->
                GameCard(
                    game = game,
                    isIndonesian = isIndonesian,
                    onDetailClick = { onDetailClick(game.id) },
                    onBrowserClick = { onBrowserClick(game.steamLink) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onSettingsClick: () -> Unit,
    onExit: () -> Unit,
    isIndonesian: Boolean
) {
    TopAppBar(
        title = {
            Text(stringResource(
                id = if (isIndonesian) R.string.header_titleId else R.string.header_titleEn
            ))
        },
        navigationIcon = {
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(
                        id = if (isIndonesian) R.string.settings_buttonId else R.string.settings_button
                    )
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        games = gameSample,
        isIndonesian = false,
        onDetailClick = {},
        onBrowserClick = {},
        onSettingsClick = {},
        onExit = {},
    )
}