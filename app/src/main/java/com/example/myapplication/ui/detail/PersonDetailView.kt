package com.example.myapplication.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailView(
    person: Person,
    imageUrl: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(person.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←", style = MaterialTheme.typography.headlineSmall)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = person.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetailRow(label = "Height", value = "${person.height} cm")
            DetailRow(label = "Mass", value = "${person.mass} kg")
            DetailRow(label = "Hair color", value = person.hairColor)
            DetailRow(label = "Skin color", value = person.skinColor)
            DetailRow(label = "Eye color", value = person.eyeColor)
            DetailRow(label = "Birth year", value = person.birthYear)
            DetailRow(label = "Gender", value = person.gender)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
private fun PersonDetailViewPreview() {
    PersonDetailView(
        person = Person(
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
        ),
        onBackClick = {},
        imageUrl = TODO(),
        modifier = TODO()
    )
}