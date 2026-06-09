package com.example.moviecatalog

import android.app.Application
import com.example.moviecatalog.data.LanguagePreference
import com.example.moviecatalog.data.local.MovieDatabase
import com.example.moviecatalog.data.network.RetrofitInstance
import com.example.moviecatalog.data.repository.MovieRepository

class MovieCatalogApplication : Application() {
    val database by lazy { MovieDatabase.getDatabase(this) }
    val repository by lazy { MovieRepository(RetrofitInstance.api, database.movieDao()) }
    val languagePreference by lazy { LanguagePreference(this) }
}