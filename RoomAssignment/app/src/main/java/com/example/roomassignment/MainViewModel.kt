package com.example.roomassignment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomassignment.data.db.AppDatabase
import com.example.roomassignment.data.entity.BookEntity
import com.example.roomassignment.data.repository.FantasyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repository = FantasyRepository(database.genreDao(), database.bookDao())

    val genresWithBooks = repository.genresWithBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _navigateToDetail = MutableStateFlow<Int?>(null)
    val navigateToDetail = _navigateToDetail.asStateFlow()

    private val _openBrowser = MutableStateFlow<String?>(null)
    val openBrowser = _openBrowser.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.seedIfEmpty()
        }
    }

    fun onBookClick(bookId: Int) {
        Timber.d("Book selected, id=$bookId")
        _navigateToDetail.value = bookId
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun onLinkClick(url: String) {
        Timber.d("Opening link: $url")
        _openBrowser.value = url
    }

    fun onBrowserOpened() {
        _openBrowser.value = null
    }

    fun findBook(bookId: Int): BookEntity? =
        genresWithBooks.value.flatMap { it.books }.find { it.id == bookId }

    fun findGenreName(genreId: Int): String =
        genresWithBooks.value.find { it.genre.id == genreId }?.genre?.name.orEmpty()
}