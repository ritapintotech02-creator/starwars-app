package com.example.myapplication.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.data.model.Person

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    person: Person,
    imageUrl: String?,
    onBackClick: () -> Unit,
) {
    PersonDetailView(
        modifier = modifier,
        person = person,
        imageUrl = imageUrl,
        onBackClick = onBackClick,
    )
}