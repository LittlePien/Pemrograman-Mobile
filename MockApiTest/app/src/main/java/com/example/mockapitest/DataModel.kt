package com.example.mockapitest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "message") val message: String,
    @Json(name = "code") val code: String,
    @Json(name = "data") val data: DataBody,
)

@JsonClass(generateAdapter = true)
data class DataBody(
    @Json(name = "text") val text: String
)