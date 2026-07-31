package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Person
import com.example.myapplication.data.model.SwapiResponse
import com.example.myapplication.data.remote.RetrofitInstance

class PersonRepository {

    private val api = RetrofitInstance.api

    suspend fun getPeople(page: Int = 1, search: String? = null): SwapiResponse<Person> {
        return api.getPeople(page = page, search = search)
    }
}