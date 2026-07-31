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
    val uiState by viewModel.uiState.collectAsState()

    PersonListView(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onPersonClick = onPersonClick,
        onLoadNextPage = viewModel::loadNextPage,
        modifier = modifier
    )
}