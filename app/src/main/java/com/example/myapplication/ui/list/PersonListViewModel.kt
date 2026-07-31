package com.example.myapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.PersonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonListViewModel(
    private val repository: PersonRepository = PersonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonListUiState())
    val uiState: StateFlow<PersonListUiState> = _uiState.asStateFlow()

    init {
        loadPeople()
    }

    fun loadPeople(page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val response = repository.getPeople(
                    page = page,
                    search = _uiState.value.searchQuery.ifBlank { null }
                )
                val updatedList = if (page == 1) {
                    response.results
                } else {
                    _uiState.value.people + response.results
                }
                _uiState.value = _uiState.value.copy(
                    people = updatedList,
                    isLoading = false,
                    currentPage = page,
                    hasNextPage = response.next != null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load characters: ${e.message}"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        loadPeople(page = 1)
    }

    fun loadNextPage() {
        if (_uiState.value.hasNextPage && !_uiState.value.isLoading) {
            loadPeople(page = _uiState.value.currentPage + 1)
        }
    }
}