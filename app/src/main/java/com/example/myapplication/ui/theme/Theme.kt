package com.example.myapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
private val DarkColorScheme = darkColorScheme(
    primary = SwYellow,
    onPrimary = SwBlack,
    secondary = SwYellowDark,
    onSecondary = SwBlack,
    background = SwBlack,
    onBackground = SwLightGray,
    surface = SwDarkGray,
    onSurface = SwLightGray,
    error = SwRed
)

private val LightColorScheme = lightColorScheme(
    primary = SwYellowDark,
    onPrimary = SwBlack,
    secondary = SwYellow,
    onSecondary = SwBlack,
    background = SwLightGray,
    onBackground = SwBlack,
    surface = Color.White,
    onSurface = SwBlack,
    error = SwRed
)
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}