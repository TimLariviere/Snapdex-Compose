package com.kanoyatech.snapdex.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val snapdexDarkGray = Color(0xFF555555)
val snapdexDarkGray2 = Color(0xFF2e2e2e)
val snapdexBlack = Color(0xFF000000)
val snapdexWhite = Color(0xFFFFFFFF)
val snapdexGreen = Color(0xFF63BC5A)

data object ElementColor {
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
    primary = snapdexBlack,
    onSurfaceVariant = snapdexWhite
)

val DarkColors = darkColorScheme(
    primary = snapdexDarkGray2
)