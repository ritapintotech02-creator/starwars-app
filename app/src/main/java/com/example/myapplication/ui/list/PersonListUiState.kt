package com.example.myapplication.ui.list

import com.example.myapplication.data.model.Person

enum class SortOption { NAME, BIRTH_YEAR }
data class PersonListUiState(
    val people: List<Person> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
    val genderFilter: String? = null,
    val sortOption: SortOption = SortOption.NAME
)