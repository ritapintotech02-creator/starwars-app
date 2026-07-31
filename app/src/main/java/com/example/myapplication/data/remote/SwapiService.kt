package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Person
import com.example.myapplication.data.model.SwapiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SwapiService {

    @GET("people/")
    suspend fun getPeople(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null
    ): SwapiResponse<Person>
}