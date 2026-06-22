package com.example.roomassignment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import com.example.roomassignment.data.entity.BookEntity
import com.example.roomassignment.ui.components.BookCoverPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    book: BookEntity,
    genreName: String,
    onLinkClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = book.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
                .padding(16.dp)
        ) {
            BookCoverPlaceholder(
                title = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "by ${book.author} · ${book.year}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Genre: $genreName",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "★ ${book.rating}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Synopsis", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = book.synopsis,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Button(
                onClick = { onLinkClick(book.link) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Find it on Open Library")
            }
        }
    }
}