package com.example.kotlin.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    secondary = Color(0xFF2CFFB5),
    tertiary = Color(0xFFFFD400),
    background = Color(0xFF000141),
    surface = Color(0xFFE0E0E0),
    onPrimary = Color(0xFFFF7700),
    onSecondary = Color(0xFF07FFE3),
    onTertiary =Color(0xFF6B04FC),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFF757575),
    secondaryContainer = Color(0xFF3847D1),

)

private val LightColorScheme = lightColorScheme(
//        primary = Purple40,
//        secondary = PurpleGrey40,
//        tertiary = Pink40,
//
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
    primary = Color(0xFF030F4B),
    secondary = Color(0xFFFF00BF),
    tertiary = Color(0xFFFF00BF),
    background = Color(0xFF000141),
    surface = Color(0xFFFF00BF),
    onPrimary = Color(0xFFFF00BF),
    onSecondary = Color(0xFFFF00BF),
    onTertiary =Color(0xFFFF00BF),
    onBackground = Color(0xFF060861),
    onSurface = Color(0xFFFF00BF),
)

@Composable
fun MyApplicationTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
) {


    Log.i("Info", "MyApplicationTheme: ")

    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//
//            Log.i("Info", "When accomplish: ")
//
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {

        Log.i("Info", "Is not edit or what???: ")

        SideEffect {
            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
    )
}