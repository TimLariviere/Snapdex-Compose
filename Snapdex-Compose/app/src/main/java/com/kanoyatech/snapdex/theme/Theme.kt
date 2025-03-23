package com.kanoyatech.snapdex.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val colors = if (isDarkTheme) DarkColors else LightColors

    CompositionLocalProvider(
        LocalTypography provides SnapdexTypography,
        LocalColors provides colors,
        LocalShapes provides Shapes(),
        LocalTextStyle provides SnapdexTypography.paragraph,
        content = content
    )
}

object SnapdexTheme {
    val typography: Typography @Composable get() = LocalTypography.current
    val colorScheme: ColorScheme @Composable get() = LocalColors.current
    val shapes: Shapes @Composable get() = LocalShapes.current
}