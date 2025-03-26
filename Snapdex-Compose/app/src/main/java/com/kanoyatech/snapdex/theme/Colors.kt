package com.kanoyatech.snapdex.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data object TypeColor {
    val Normal = Color(0xFF919AA2)
    val Fire = Color(0xFFFF9D55)
    val Water = Color(0xFF5090D6)
    val Grass = Color(0xFF63BC5A)
    val Electric = Color(0xFFF4D23C)
    val Ice = Color(0xFF73CEC0)
    val Fighting = Color(0xFFCE416B)
    val Poison = Color(0xFFB567CE)
    val Ground = Color(0xFFD97845)
    val Flying = Color(0xFF89AAE3)
    val Psychic = Color(0xFFFA7179)
    val Bug = Color(0xFF91C12F)
    val Rock = Color(0xFFC5B78C)
    val Ghost = Color(0xFF5269AD)
    val Dragon = Color(0xFF0B6DC3)
    val Steel = Color(0xFF5A8EA2)
    val Fairy = Color(0xFFEC8FE6)

    // Color for anything on top of the type colors
    val OnType = Color(0xFFFFFFFF)
}

@Immutable
data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val background: Color,
    val backgroundVariant: Color,
    val onBackground: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val surfaceContainer: Color, // Tab bar
    val outline: Color,
    val inOutline: Color,
    val success: Color,
    val error: Color,
    val onError: Color
)

val LightColors = ColorScheme(
    primary = Color(0xFFFF6999),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF2C73FF),
    background = Color(0xFFFFF0A2),
    backgroundVariant = Color(0xFFFFCCD8),
    onBackground = Color(0xFF68635E),
    surface = Color(0x4DFFFFFF),
    surfaceVariant = Color(0xFFFFE9CF),
    onSurface = Color(0xFF68635E),
    onSurfaceVariant = Color(0x8068635E),
    surfaceContainer = Color(0xFF0000FF),
    outline = Color(0xFFFFD9C3),
    inOutline = Color(0xFFFFFFFF),
    success = Color(0xFF059F30),
    error = Color(0xFFD10303),
    onError = Color(0xFFFFFFFF)
)

val DarkColors = ColorScheme(
    primary = Color(0xFFFF6999),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF2C73FF),
    background = Color(0xFFFFF0A2),
    backgroundVariant = Color(0xFFFFCCD8),
    onBackground = Color(0xFF68635E),
    surface = Color(0x4DFFFFFF),
    surfaceVariant = Color(0xFFFFE9CF),
    onSurface = Color(0xFF68635E),
    onSurfaceVariant = Color(0xFF68635E),
    surfaceContainer = Color(0xFF0000FF),
    outline = Color(0xFFFFD9C3),
    inOutline = Color(0xFFFFFFFF),
    success = Color(0xFF00FF00),
    error = Color(0xFFFF0000),
    onError = Color(0xFFFFFFFF)
)

val LocalColors = staticCompositionLocalOf { LightColors }