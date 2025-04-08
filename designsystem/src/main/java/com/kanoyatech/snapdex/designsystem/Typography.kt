@file:OptIn(ExperimentalTextApi::class)

package com.kanoyatech.snapdex.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Poppins = FontFamily(
    Font(
        R.font.poppins_regular,
        FontWeight.Normal
    ),
    Font(
        R.font.poppins_medium,
        FontWeight.Medium
    ),
    Font(
        R.font.poppins_semibold,
        FontWeight.SemiBold
    )
)

@Immutable
data class Typography(
    val heading1: TextStyle,
    val heading2: TextStyle,
    val heading3: TextStyle,
    val paragraph: TextStyle,
    val primaryButton: TextStyle,
    val secondaryButton: TextStyle,
    val smallLabel: TextStyle,
    val largeLabel: TextStyle
)

val SnapdexTypography = Typography(
    heading1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 32.sp
    ),
    heading2 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 24.sp
    ),
    heading3 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 20.sp
    ),
    paragraph = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 17.sp
    ),
    primaryButton = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    secondaryButton = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.sp
    ),
    smallLabel = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp
    ),
    largeLabel = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

val LocalTypography = staticCompositionLocalOf { SnapdexTypography }