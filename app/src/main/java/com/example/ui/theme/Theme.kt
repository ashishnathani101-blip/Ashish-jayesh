package com.example.ui.theme

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
    primary = MedTealPrimaryDark,
    onPrimary = MedTealOnPrimaryDark,
    primaryContainer = MedTealContainerDark,
    onPrimaryContainer = MedTealOnContainerDark,
    secondary = MedSlateContainer,
    onSecondary = MedSlateOnContainer,
    tertiary = MedAlertContainer,
    background = MedBackgroundDark,
    surface = MedSurfaceDark,
    surfaceVariant = MedSurfaceVariantDark,
    onBackground = MedOnSurfaceDark,
    onSurface = MedOnSurfaceDark,
    outline = MedOutlineVariant
)

private val LightColorScheme = lightColorScheme(
    primary = MedTealPrimary,
    onPrimary = MedTealOnPrimary,
    primaryContainer = MedTealContainer,
    onPrimaryContainer = MedTealOnContainer,
    secondary = MedSlateSecondary,
    onSecondary = MedSlateOnSecondary,
    secondaryContainer = MedSlateContainer,
    onSecondaryContainer = MedSlateOnContainer,
    tertiary = MedAlertTertiary,
    tertiaryContainer = MedAlertContainer,
    onTertiaryContainer = MedAlertOnContainer,
    background = MedBackground,
    surface = MedSurface,
    surfaceVariant = MedSurfaceVariant,
    onBackground = MedOnSurface,
    onSurface = MedOnSurface,
    outline = MedOutline,
    outlineVariant = MedOutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Light theme by default for clean medical clinical look
    // We prefer our polished clinical colors for a unified medical experience
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
