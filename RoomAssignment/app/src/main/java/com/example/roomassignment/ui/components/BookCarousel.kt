package com.example.roomassignment.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.roomassignment.data.entity.BookEntity
import androidx.compose.foundation.lazy.items

@Composable
fun BookCarouselCard(
    book: BookEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .width(140.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            BookCoverPlaceholder(
                title = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BookCarousel(
    books: List<BookEntity>,
    onBookClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(listState)
    ) {
        items(books) { book ->
            BookCarouselCard(
                book = book,
                onClick = { onBookClick(book.id) }
            )
        }
    }
}