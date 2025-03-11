package com.kanoyatech.snapdex.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val snapdexDarkGray2 = Color(0xFF2e2e2e)
val snapdexWhite = Color(0xFFFFFFFF)

val snapdexLightGray = Color(0xFFE5E5E5)
val snapdexGray = Color(0xFF4D4D4D)
val snapdexGray2 = Color(0xFFF2F2F2)
val snapdexGray3 = Color(0xFF666666)
val snapdexBlack = Color(0xFF000000)
val snapdexDarkBlue2 = Color(0xFF173EA5)
val snapdexDarkBlue = Color(0xFF2551C3)
val snapdexLightRed = Color(0xFFFF7596)
val snapdexRed = Color(0xFFFD525C)

val snapdexGray100 = Color(0xFFE6E6E6)
val snapdexGray400 = Color(0xFF999999)

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
    primary = snapdexDarkBlue2, // default button color
    onPrimary = snapdexWhite, // default button text color
    secondary = snapdexGray3,
    surface = snapdexLightGray,
    onSurface = snapdexGray2,
    onSurfaceVariant = snapdexGray400,
    onBackground = snapdexGray // default text color
)

val DarkColors = darkColorScheme(
    primary = snapdexDarkGray2
)