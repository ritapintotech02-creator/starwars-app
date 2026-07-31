package com.example.myapplication.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.Person

@Composable
fun PersonListView(
    uiState: PersonListUiState,
    onSearchQueryChanged: (String) -> Unit,
    onPersonClick: (Person) -> Unit,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            label = { Text("Search characters") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        when {
            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.errorMessage)
                }
            }

            uiState.people.isEmpty() && uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(uiState.people) { person ->
                        PersonListItem(
                            person = person,
                            onClick = { onPersonClick(person) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        if (uiState.hasNextPage) {
                            LaunchedPagination(onLoadNextPage = onLoadNextPage)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonListItem(person: Person, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = person.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Birth year: ${person.birthYear} · Gender: ${person.gender}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun LaunchedPagination(onLoadNextPage: () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        onLoadNextPage()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonListViewPreview() {
    val sampleState = PersonListUiState(
        people = listOf(
            Person(
                name = "Luke Skywalker",
                height = "172",
                mass = "77",
                hairColor = "blond",
                skinColor = "fair",
                eyeColor = "blue",
                birthYear = "19BBY",
                gender = "male",
                homeworld = "",
                url = ""
            )
        )
    )
    PersonListView(
        uiState = sampleState,
        onSearchQueryChanged = {},
        onPersonClick = {},
        onLoadNextPage = {}
    )
}