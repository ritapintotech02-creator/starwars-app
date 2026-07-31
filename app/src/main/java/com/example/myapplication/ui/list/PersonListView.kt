package com.example.myapplication.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Person
import com.example.myapplication.data.model.imageFor

@Composable
fun PersonListView(
    modifier: Modifier = Modifier,
    uiState: PersonListUiState,
    imageMap: Map<String, String>,
    onLoadNextPage: () -> Unit,
    onPersonClick: (Person) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onGenderFilterChanged: (String?) -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearchQueryChanged,
            label = { Text("Search characters") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Sort by:",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            FilterChip(
                selected = uiState.genderFilter == "male",
                onClick = { onGenderFilterChanged(if (uiState.genderFilter == "male") null else "male") },
                label = { Text("Male") }
            )
            FilterChip(
                selected = uiState.genderFilter == "female",
                onClick = { onGenderFilterChanged(if (uiState.genderFilter == "female") null else "female") },
                label = { Text("Female") }
            )
            FilterChip(
                selected = uiState.sortOption == SortOption.NAME,
                onClick = { onSortOptionChanged(SortOption.NAME) },
                label = { Text("A-Z") }
            )
            FilterChip(
                selected = uiState.sortOption == SortOption.BIRTH_YEAR,
                onClick = { onSortOptionChanged(SortOption.BIRTH_YEAR) },
                label = { Text("Birth Year") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val displayedPeople = uiState.people
            .filter { uiState.genderFilter == null || it.gender == uiState.genderFilter }
            .let { list ->
                when (uiState.sortOption) {
                    SortOption.NAME -> list.sortedBy { it.name }
                    SortOption.BIRTH_YEAR -> list.sortedBy { it.birthYear }
                }
            }

        when {
            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.errorMessage)
                }
            }

            uiState.people.isEmpty() && uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(displayedPeople) { person ->
                        PersonListItem(
                            person = person,
                            imageUrl = imageMap.imageFor(person),
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
private fun PersonListItem(person: Person, imageUrl: String?, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = person.name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = person.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Birth year: ${person.birthYear} · Gender: ${person.gender}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun LaunchedPagination(onLoadNextPage: () -> Unit) {
    LaunchedEffect(Unit) { onLoadNextPage() }
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
        imageMap = emptyMap(),
        onSearchQueryChanged = {},
        onGenderFilterChanged = {},
        onSortOptionChanged = {},
        onPersonClick = {},
        onLoadNextPage = {}
    )
}