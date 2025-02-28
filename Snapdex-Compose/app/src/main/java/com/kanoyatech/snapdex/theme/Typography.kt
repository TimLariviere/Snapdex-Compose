@file:OptIn(ExperimentalTextApi::class)

package com.kanoyatech.snapdex.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R

val OpenSans = FontFamily(
    Font(
        R.font.open_sans,
        FontWeight.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400)
        )
    ),
    Font(
        R.font.open_sans,
        FontWeight.Medium,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    )
)

val Poppins = FontFamily(
    Font(
        R.font.poppins_regular,
        FontWeight.Normal
    ),
    Font(
        R.font.poppins_medium,
        FontWeight.Medium
    )
)

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    )
)