package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ChercherDarkColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = Navy950,
    primaryContainer = Navy800,
    onPrimaryContainer = CyanAccent,
    secondary = Indigo400,
    onSecondary = Navy950,
    secondaryContainer = Navy850,
    onSecondaryContainer = Indigo400,
    tertiary = Amber400,
    onTertiary = Navy950,
    background = Navy950,
    onBackground = Color.White,
    surface = Navy900,
    onSurface = Color.White,
    surfaceVariant = Navy800,
    onSurfaceVariant = Navy200,
    outline = Navy700,
    outlineVariant = Navy800,
    error = Rose500,
    onError = Color.White
)

private val ChercherLightColorScheme = lightColorScheme(
    primary = RoyalBlue600,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = Indigo600,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0E7FF),
    onSecondaryContainer = Color(0xFF312E81),
    tertiary = Amber500,
    onTertiary = Color.White,
    background = Navy50,
    onBackground = Navy950,
    surface = Color.White,
    onSurface = Navy950,
    surfaceVariant = Navy100,
    onSurfaceVariant = Navy700,
    outline = Navy200,
    outlineVariant = Color(0xFFE2E8F0),
    error = Rose500,
    onError = Color.White
)

@Composable
fun ChercherTimetableTheme(
    themePreference: String = "system", // "system", "light", "dark"
    content: @Composable () -> Unit
) {
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themePreference) {
        "dark" -> true
        "light" -> false
        else -> systemInDark
    }

    val colorScheme = if (isDark) ChercherDarkColorScheme else ChercherLightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !isDark
                controller.isAppearanceLightNavigationBars = !isDark
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
