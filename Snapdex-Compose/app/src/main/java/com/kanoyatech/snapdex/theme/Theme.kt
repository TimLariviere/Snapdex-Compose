package com.kanoyatech.snapdex.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val colors = if (isDarkTheme) DarkColors else LightColors
    val customColors = if (isDarkTheme) DarkCustomColors else LightCustomColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalCustomColors provides customColors,
            content = content
        )
    }
}