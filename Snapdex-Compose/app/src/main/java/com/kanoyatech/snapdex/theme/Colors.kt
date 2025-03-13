package com.kanoyatech.snapdex.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
    val displayLarge: Color,
    val titleMedium: Color,
    val titleSmall: Color,
    val labelLarge: Color
)

// Validated colors
val snapdexWhite = Color(0xFFFFFFFF)
val snapdexBlack = Color(0xFF000000)
val snapdexBlue400 = Color(0xFF2551C3)
val snapdexBlue700 = Color(0xFF173EA5)
val snapdexRed200 = Color(0xFFFF7596)
val snapdexGray200 = Color(0xFFCCCCCC)
val snapdexGray400 = Color(0xFF999999)
val snapdexGray700 = Color(0xFF4D4D4D)

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
    val Dark = Color(0xFF5A5465)
    val Steel = Color(0xFF5A8EA2)
    val Fairy = Color(0xFFEC8FE6)
}

val LightColors = lightColorScheme(
    primary = snapdexBlue700, // default button color
    onPrimary = snapdexWhite, // default button text color
    primaryContainer = snapdexBlue700, // default floating action button background
    onSecondaryContainer = snapdexWhite, // default active tab icon
    secondaryContainer = snapdexBlue700, // default active tab background
    background = snapdexWhite, // default page color
    onBackground = snapdexGray700, // default text color
    surface = snapdexWhite, // default unchecked toggle thumb
    surfaceVariant = snapdexGray200, // default unchecked toggle track
    onSurface = snapdexGray700, // default disabled button container (12% alpha), disabled label text (38% alpha), active tab label
    onSurfaceVariant = snapdexGray400, // default placeholder, inactive tab label/icon
    outlineVariant = snapdexGray200 // default horizontal divider
)

val LightCustomColors = CustomColorScheme(
    displayLarge = snapdexBlack,
    titleMedium = snapdexBlack,
    titleSmall = snapdexBlack,
    labelLarge = snapdexBlack
)

val DarkColors = darkColorScheme()

val DarkCustomColors = CustomColorScheme(
    displayLarge = snapdexBlack,
    titleMedium = snapdexBlack,
    titleSmall = snapdexBlack,
    labelLarge = snapdexBlack
)

val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

val MaterialTheme.customColorScheme: CustomColorScheme
    @Composable
    get() = LocalCustomColors.current