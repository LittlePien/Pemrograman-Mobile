package com.example.gamelistcomp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamelistcomp.R
import com.example.gamelistcomp.data.Game
import com.example.gamelistcomp.data.gameSample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    game: Game,
    isIndonesian: Boolean,
    onSettingsClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
      topBar = {
          CenterAlignedTopAppBar(
              title = {
                  Text(text = stringResource(id = game.title))
              },
              navigationIcon = {
                  IconButton(onClick = onBackClick) {
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
                          contentDescription = stringResource(R.string.settings_button)
                      )
                  }
              }
          )
      }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = game.detailImageRes),
                contentDescription = stringResource(id = game.title),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = game.title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = game.ratings),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = stringResource(id = game.review),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(
                                id = if (isIndonesian) R.string.desc_textId else R.string.desc_textEn
                            ),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = stringResource(
                                id = if (isIndonesian) game.descriptionId else game.descriptionEn
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        game = gameSample[0],
        isIndonesian = false,
        onSettingsClick = {},
        onBackClick = {}
    )
}