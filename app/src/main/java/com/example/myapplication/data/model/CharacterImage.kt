package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterImage(
    val name: String,
    val image: String? = null
)

fun Map<String, String>.imageFor(person: Person): String? =
    this[person.name.lowercase()]