package com.example.myapplication.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.model.Person

@Composable
fun PersonListScreen(
    onPersonClick: (Person) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonListViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val imageMap by viewModel.imageMap.collectAsState()

    PersonListView(
        modifier = modifier,
        uiState = state,
        imageMap = imageMap,
        onSearchQueryChanged = { query -> viewModel.onSearchQueryChanged(query) },
        onGenderFilterChanged = { gender -> viewModel.onGenderFilterChanged(gender) },
        onSortOptionChanged = { option -> viewModel.onSortOptionChanged(option) },
        onPersonClick = onPersonClick,
        onLoadNextPage = { viewModel.loadNextPage() },
    )
}