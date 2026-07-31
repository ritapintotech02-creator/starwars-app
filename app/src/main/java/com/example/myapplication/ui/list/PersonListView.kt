package com.example.myapplication.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Person
import com.example.myapplication.data.model.imageFor
import com.example.myapplication.ui.theme.StarfieldBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListView(
    modifier: Modifier = Modifier,
    uiState: PersonListUiState,
    imageMap: Map<String, String>,
    onSearchQueryChanged: (String) -> Unit,
    onGenderFilterChanged: (String?) -> Unit,
    onSortOptionChanged: (SortOption) -> Unit,
    onPersonClick: (Person) -> Unit,
    onLoadNextPage: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        StarfieldBackground(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChanged,
                label = { Text("Search characters") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.sortOption == SortOption.NAME,
                    onClick = { onSortOptionChanged(SortOption.NAME) },
                    label = { Text("A-Z") }
                )
                FilterChip(
                    selected = uiState.sortOption == SortOption.BIRTH_YEAR,
                    onClick = { onSortOptionChanged(SortOption.BIRTH_YEAR) },
                    label = { Text("Birth year") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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
                        Text(text = uiState.errorMessage, color = MaterialTheme.colorScheme.error)
                    }
                }

                uiState.people.isEmpty() && uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Scanning the archives...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
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
                            Spacer(modifier = Modifier.height(12.dp))
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
}

@Composable
private fun PersonListItem(person: Person, imageUrl: String?, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = person.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = person.name.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Birth year: ${person.birthYear} · Gender: ${person.gender}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun LaunchedPagination(onLoadNextPage: () -> Unit) {
    LaunchedEffect(Unit) { onLoadNextPage() }
    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.primary)
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
        onLoadNextPage = {},
        modifier = Modifier,
    )
}