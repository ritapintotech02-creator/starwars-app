package com.example.myapplication.data.remote

import com.example.myapplication.data.model.CharacterImage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface ImageApiService {
    @GET("all.json")
    suspend fun getAllCharacterImages(): List<CharacterImage>
}

object ImageApiInstance {
    private const val BASE_URL = "https://akabab.github.io/starwars-api/api/"

    private val json = Json { ignoreUnknownKeys = true }

    val api: ImageApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ImageApiService::class.java)
    }
}