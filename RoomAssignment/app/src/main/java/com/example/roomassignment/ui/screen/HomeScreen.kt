package com.example.roomassignment.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.roomassignment.data.entity.GenreWithBooks
import com.example.roomassignment.ui.components.BookCard
import com.example.roomassignment.ui.components.BookCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    genresWithBooks: List<GenreWithBooks>,
    onBookClick: (Int) -> Unit,
    onLinkClick: (String) -> Unit
) {
    val allBooks = genresWithBooks.flatMap { it.books }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Fantasy Book List") })
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            if (allBooks.isNotEmpty()) {
                item {
                    Text(
                        text = "All Books",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                    BookCarousel(books = allBooks, onBookClick = onBookClick)
                }
            }

            genresWithBooks.forEach { genreWithBooks ->
                item {
                    Column(modifier = Modifier.padding(top = 16.dp)) {
                        Text(
                            text = genreWithBooks.genre.name,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = genreWithBooks.genre.description,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
                items(genreWithBooks.books) { book ->
                    BookCard(
                        book = book,
                        onClick = { onBookClick(book.id) },
                        onLinkClick = { onLinkClick(book.link) }
                    )
                }
            }
        }
    }
}