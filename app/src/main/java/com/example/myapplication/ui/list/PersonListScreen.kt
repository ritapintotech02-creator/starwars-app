package com.example.myapplication.ui.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.model.Person

@Composable
fun PersonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonListViewModel = viewModel(),
    onPersonClick: (Person) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val imageMap by viewModel.imageMap.collectAsState()

    PersonListView(
        uiState = state,
        imageMap = imageMap,
        onSearchQueryChanged = { query -> viewModel.onSearchQueryChanged(query) },
        onPersonClick = onPersonClick,
        onLoadNextPage = { viewModel.loadNextPage() },
        modifier = modifier
    )
}