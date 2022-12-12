package com.emmanuel.pastor.simplesmartapps.tricycle.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = FadedBlue,
    onPrimary = ElectricBlue,
    secondary = FadedBlueA15,
    tertiary = FadedOrange,
    surface = DarkGrey,
    onSurface = Color.White,
    background = AlmostBlack,
    onBackground = Color.White,
    error = FadedErrorRed,
    secondaryContainer = FadedBlueA15,
    onSecondaryContainer = Color.White,
    onSurfaceVariant = FadedOffGrey
)


private val LightColorScheme = lightColorScheme(
    primary = MainBlue,
    secondary = MainBlueA15,
    tertiary = MainOrange,
    surface = Color.White,
    onSurface = Color.Black,
    background = BlueWhite,
    onBackground = Color.Black,
    error = ErrorRed,
    secondaryContainer = MainBlueA15,
    onSecondaryContainer = Color.Black,
    onSurfaceVariant = OffGrey
)

val ColorScheme.success: Color
    @Composable get() = if (isSystemInDarkTheme()) FadedGreen500 else Green500
val ColorScheme.warning: Color
    @Composable get() = if (isSystemInDarkTheme()) FadedOrange else MainOrange
val ColorScheme.onSurfaceOff: Color
    @Composable get() = if (isSystemInDarkTheme()) FadedOffGrey else OffGrey
val ColorScheme.onSurfaceDiscrete: Color
    @Composable get() = Grey500

@Composable
fun TricycleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        val currentWindow = (view.context as? Activity)?.window ?: throw Exception("Not in an activity - unable to get Window reference")
        SideEffect {
            currentWindow.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}