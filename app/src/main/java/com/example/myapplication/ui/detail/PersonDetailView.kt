package com.example.myapplication.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import coil.compose.AsyncImage
import com.example.myapplication.data.model.Person
import com.example.myapplication.ui.theme.StarfieldBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailView(
    modifier: Modifier = Modifier,
    person: Person,
    imageUrl: String?,
    onBackClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        StarfieldBackground(modifier = Modifier.fillMaxSize())

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                                    .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBackIosNew,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = person.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f)),
                                    startY = 120f
                                )
                            )
                    )
                    Text(
                        text = person.name.uppercase(),
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "SUBJECT FILE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(label = "HEIGHT", value = "${person.height} cm")
                    DetailRow(label = "MASS", value = "${person.mass} kg")
                    DetailRow(label = "HAIR COLOR", value = person.hairColor)
                    DetailRow(label = "SKIN COLOR", value = person.skinColor)
                    DetailRow(label = "EYE COLOR", value = person.eyeColor)
                    DetailRow(label = "BIRTH YEAR", value = person.birthYear)
                    DetailRow(label = "GENDER", value = person.gender)

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        thickness = 0.5.dp
    )
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
        imageUrl = null,
        onBackClick = {}
    )
}