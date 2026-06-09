package com.example.moviecatalog.ui.screen

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.moviecatalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onLanguageChanged: () -> Unit,
    onBackClick: () -> Unit
) {
    var isIndonesian by remember {
        mutableStateOf(AppCompatDelegate.getApplicationLocales().toLanguageTags().contains("id"))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_button)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.language_text),
                    style = MaterialTheme.typography.titleLarge
                )

                Switch(
                    checked = isIndonesian,
                    onCheckedChange = { isChecked ->
                        isIndonesian = isChecked
                        val localeTag = if (isChecked) "id" else "en"
                        val appLocale = LocaleListCompat.forLanguageTags(localeTag)
                        AppCompatDelegate.setApplicationLocales(appLocale)
                        onLanguageChanged()
                    }
                )
            }

            Text(
                text = stringResource(if (isIndonesian) R.string.Id_text else R.string.En_text),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}