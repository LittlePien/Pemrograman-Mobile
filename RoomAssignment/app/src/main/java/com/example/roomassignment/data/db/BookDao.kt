package com.example.roomassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomassignment.data.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<BookEntity>): List<Long>

    @Query("SELECT * FROM books WHERE id = :bookId LIMIT 1")
    fun getBookById(bookId: Int): Flow<BookEntity>

    @Query("SELECT * FROM books WHERE genreId = :genreId ORDER BY id ASC")
    fun getBooksByGenre(genreId: Int): Flow<List<BookEntity>>

    @Query("SELECT COUNT(*) FROM books")
    suspend fun count(): Int
}