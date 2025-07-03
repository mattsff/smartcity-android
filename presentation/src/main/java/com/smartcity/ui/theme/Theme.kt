package com.smartcity.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = BluePrimaryDark,
    secondary = BlueSecondaryDark,
    background = BlueBackgroundDark,
    surface = BlueSurfaceDark,
    error = BlueErrorDark,
    onPrimary = BlueOnPrimaryDark,
    onSecondary = BlueOnSecondaryDark,
    onBackground = BlueOnBackgroundDark,
    onSurface = BlueOnSurfaceDark,
    onError = BlueOnErrorDark
)

private val LightColorPalette = lightColorScheme(
    primary = BluePrimaryLight,
    secondary = BlueSecondaryLight,
    background = BlueBackgroundLight,
    surface = BlueSurfaceLight,
    error = BlueErrorLight,
    onPrimary = BlueOnPrimaryLight,
    onSecondary = BlueOnSecondaryLight,
    onBackground = BlueOnBackgroundLight,
    onSurface = BlueOnSurfaceLight,
    onError = BlueOnErrorLight
)

@Composable
fun SmartCityAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

