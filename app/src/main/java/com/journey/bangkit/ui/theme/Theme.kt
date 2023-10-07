package com.journey.bangkit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Light,
    secondary = Dark,
    background = Light
)

private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    onPrimary = DarkGray40,
    secondary = Light,
    background = Black
)

@Composable
fun JourneyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setSystemBarsColor(color = Blue40, darkIcons = false)
    systemUiController.setNavigationBarColor(color = Light, darkIcons = true)

//    if (darkTheme) {
//        systemUiController.setSystemBarsColor(color = DarkColorScheme.background, darkIcons = false)
//        systemUiController.setNavigationBarColor( color = DarkColorScheme.background, darkIcons = false)
//    }else{
//        systemUiController.setSystemBarsColor(color = LightColorScheme.primary, darkIcons = false)
//        systemUiController.setNavigationBarColor(color = LightColorScheme.primary, darkIcons = false)
//    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}