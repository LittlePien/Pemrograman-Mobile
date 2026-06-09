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

//    Strategi Caching: Cache-Then-Network (Stale-While-Revalidate)
//    Mekanisme kerja:
//    1. Sistem mengawali proses dengan memancarkan status Loading.
//    2. Menampilkan data dari penyimpanan lokal (Room) secara instan agar antarmuka
//       pengguna dapat langsung memuat konten awal.
//    3. Di saat yang bersamaan pada latar belakang, aplikasi mengambil data terbaru
//       dari jaringan.
//    4. Sistem menimpa cache yang lama dengan data baru dari jaringan.
//    5. Secara otomatis memancarkan data akhir tersebut kepada pengguna.
//
//    Keunggulan strategi ini:
//    - Memberikan pengalaman pengguna yang jauh lebih responsif, mengingat konten
//      langsung tersaji tanpa hambatan waktu tunggu jaringan.
//    - Memastikan konten selalu up-to-date karena data diperbarui secara otomatis
//      setiap kali perangkat terhubung ke internet.
//    - Beroperasi dengan sangat baik dalam kondisi offline — apabila permintaan
//      jaringan gagal namun cache sudah tersedia, pengguna akan tetap melihat data
//      terakhir tanpa terganggu oleh pesan error.
//    - Menawarkan proses implementasi yang relatif lebih sederhana dan mudah dipahami
//      jika dibandingkan dengan metode caching yang lebih kompleks.

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