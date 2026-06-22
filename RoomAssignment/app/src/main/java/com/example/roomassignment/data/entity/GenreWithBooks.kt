package com.example.roomassignment.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GenreWithBooks(
    @Embedded val genre: GenreEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId"
    )
    val books: List<BookEntity>
)