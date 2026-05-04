package com.hawk.hotelgenerator.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    secondary = SoftGold,
    tertiary = SlateGray,
    background = DarkBackground,
    surface = SurfaceLuxury,
    onPrimary = DeepNavy,
    onSecondary = DeepNavy,
    onBackground = MarbleWhite,
    onSurface = OnSurfaceWhite,
    surfaceVariant = SurfaceLuxury.copy(alpha = 0.7f),
    onSurfaceVariant = MarbleWhite.copy(alpha = 0.6f)
)

@Composable
fun HotelRatingTheme(
    darkTheme: Boolean = true, // Force dark theme for luxury vibe
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
