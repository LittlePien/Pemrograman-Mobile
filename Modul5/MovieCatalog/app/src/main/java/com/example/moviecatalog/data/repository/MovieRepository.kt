package com.example.moviecatalog.data.repository

import com.example.moviecatalog.data.local.MovieDao
import com.example.moviecatalog.data.model.ApiResult
import com.example.moviecatalog.data.model.Movie
import com.example.moviecatalog.data.network.TmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieRepository(
    private val api: TmdbApiService,
    private val dao: MovieDao
) {
    fun getPopularMovies(language: String): Flow<ApiResult<List<Movie>>> = flow {
        emit(ApiResult.Loading)

        val cachedMovies = dao.getAllMovies().first()
        if (cachedMovies.isNotEmpty()) {
            emit(ApiResult.Success(cachedMovies.map { it.toMovie() }))
        }

        try {
            val response = api.getPopularMovies(language = language)
            val entities = response.results.map { it.toEntity() }
            dao.upsertMovies(entities)
            emit(ApiResult.Success(entities.map { it.toMovie() }))
        } catch (e: IOException) {
            if (cachedMovies.isEmpty()) {
                emit(ApiResult.Error("Network error. Please check your connection."))
            }
        } catch (e: HttpException) {
            if (cachedMovies.isEmpty()) {
                emit(ApiResult.Error("Server error: ${e.message()}"))
            }
        } catch (e: Exception) {
            if (cachedMovies.isEmpty()) {
                emit(ApiResult.Error(e.message ?: "Unknown error occurred"))
            }
        }
    }
}