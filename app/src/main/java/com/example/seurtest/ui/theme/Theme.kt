package com.example.seurtest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6EA8FE), // Azul más claro para contraste > 4.5:1 sobre fondo oscuro
    onPrimary = Color(0xFF001D36),
    primaryContainer = Color(0xFF00325B),
    onPrimaryContainer = Color(0xFFD1E4FF),
    secondary = SeurOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF5F1500),
    onSecondaryContainer = Color(0xFFFFDBCE),
    background = Color(0xFF121212),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF121212),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF1E1F22), // Gris oscuro algo más claro que la superficie
    onSurfaceVariant = Color(0xFFC4C6D0),
    outline = Color(0xFF8E9099),
    outlineVariant = Color(0xFF44474F),
    error = Color(0xFFF2B8B5)
)

private val LightColorScheme = lightColorScheme(
    primary = SeurBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8F1FA),
    onPrimaryContainer = SeurBlue,
    secondary = SeurOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFDEBE4),
    onSecondaryContainer = Color(0xFFB8430F),
    background = Color.White,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = SeurLightGrayBackground,
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
    error = Color(0xFFB3261E)
)

@Composable
fun SeurTestTheme(
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
