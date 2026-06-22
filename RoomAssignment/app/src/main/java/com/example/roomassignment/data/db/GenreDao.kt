package com.example.roomassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.roomassignment.data.entity.GenreEntity
import com.example.roomassignment.data.entity.GenreWithBooks
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(genres: List<GenreEntity>): List<Long>

    @Query("SELECT * FROM genres ORDER BY id ASC")
    fun getAllGenres(): Flow<List<GenreEntity>>

    @Transaction
    @Query("SELECT * FROM genres ORDER BY id ASC")
    fun getGenresWithBooks(): Flow<List<GenreWithBooks>>

    @Query("SELECT COUNT(*) FROM genres")
    suspend fun count(): Int
}