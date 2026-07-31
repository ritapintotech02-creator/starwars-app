package com.example.myapplication.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun StarfieldBackground(modifier: Modifier = Modifier, starCount: Int = 80) {
    val stars = remember {
        List(starCount) {
            Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 1.6f + 0.4f)
        }
    }

    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(Color(0xFF000000), Color(0xFF0A0A12), Color(0xFF000000))
            )
        )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEach { (xFrac, yFrac, radius) ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.5f + radius / 4f),
                    radius = radius,
                    center = Offset(xFrac * size.width, yFrac * size.height)
                )
            }
        }
    }
}