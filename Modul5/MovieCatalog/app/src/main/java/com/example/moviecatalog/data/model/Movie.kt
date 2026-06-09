package com.example.moviecatalog.data.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String
) {
    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"

    val backdropUrl: String
        get() = "https://image.tmdb.org/t/p/w780$backdropPath"

    val tmdbUrl: String
        get() = "https://www.themoviedb.org/movie/$id"

    val ratingText: String
        get() = "Rating: ${"%.1f".format(voteAverage)}/10"
}