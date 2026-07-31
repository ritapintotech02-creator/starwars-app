package com.example.myapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val height: String,
    val mass: String,
    @SerialName("hair_color") val hairColor: String,
    @SerialName("skin_color") val skinColor: String,
    @SerialName("eye_color") val eyeColor: String,
    @SerialName("birth_year") val birthYear: String,
    val gender: String,
    val homeworld: String,
    val films: List<String> = emptyList(),
    val species: List<String> = emptyList(),
    val url: String
)

@Serializable
data class SwapiResponse<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)