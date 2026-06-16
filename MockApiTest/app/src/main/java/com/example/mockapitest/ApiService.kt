package com.example.mockapitest

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object ApiService {
    private val client = HttpClient(Android)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    suspend fun fetchData(): ApiResponse? {
        return try {
            val response: HttpResponse = client.get("https://tugas-android.free.beeceptor.com/data")
            val jsonString = response.bodyAsText()
            val adapter = moshi.adapter(ApiResponse::class.java)
            adapter.fromJson(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}