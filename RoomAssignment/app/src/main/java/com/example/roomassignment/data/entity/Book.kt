package com.example.roomassignment.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["genreId"])]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genreId: Int,
    val title: String,
    val author: String,
    val year: Int,
    val rating: Float,
    val synopsis: String,
    val link: String,
)