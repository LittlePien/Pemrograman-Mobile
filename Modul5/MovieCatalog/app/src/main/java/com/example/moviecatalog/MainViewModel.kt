package com.example.moviecatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatalog.data.model.ApiResult
import com.example.moviecatalog.data.model.Movie
import com.example.moviecatalog.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

class MainViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<ApiResult<List<Movie>>>(ApiResult.Loading)
    val movies = _movies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    init {
        Timber.d("MainViewModel diinisialisasi.")
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            val currentLang = Locale.getDefault().language
            val tmdbLanguageTag = if (currentLang == "id") "id-ID" else "en-US"

            Timber.d("Memanggil API TMDB dengan bahasa: $tmdbLanguageTag")

            repository.getPopularMovies(tmdbLanguageTag).collectLatest { result ->
                _movies.value = result
            }
        }
    }

    fun loadMovieById(id: Int) {
        val currentMovies = (_movies.value as? ApiResult.Success)?.data
        _selectedMovie.value = currentMovies?.find { it.id == id }
    }

    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }
}