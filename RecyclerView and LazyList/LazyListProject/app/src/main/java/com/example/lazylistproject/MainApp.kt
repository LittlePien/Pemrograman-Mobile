package com.example.lazylistproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lazylistproject.ui.MainScreen
import com.example.lazylistproject.ui.theme.LazyListProjectTheme

@Composable
fun MainApp() {
    LazyListProjectTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}