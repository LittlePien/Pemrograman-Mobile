package com.example.moviecatalog.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.Movie

@Composable
fun MovieCard(
    movie: Movie,
    onDetailClick: () -> Unit,
    onBrowserClick: () -> Unit
) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterUrl).crossfade(true).build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(100.dp).height(140.dp).clip(RoundedCornerShape(16.dp))
            )
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = movie.ratingText, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onBrowserClick, contentPadding = PaddingValues(horizontal = 8.dp)) {
                        Text(text = stringResource(R.string.tmdb_button), style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onDetailClick, contentPadding = PaddingValues(horizontal = 8.dp)) {
                        Text(text = stringResource(R.string.detail_button), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}