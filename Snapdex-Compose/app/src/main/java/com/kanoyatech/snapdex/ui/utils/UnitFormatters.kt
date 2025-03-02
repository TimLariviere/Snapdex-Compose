package com.kanoyatech.snapdex.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.kanoyatech.snapdex.domain.Weight
import com.kanoyatech.snapdex.domain.Length
import com.kanoyatech.snapdex.domain.Percentage

@Composable
fun Weight.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f kg", this.toKgDouble())
}

@Composable
fun Length.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f m", this.toMeters())
}

@Composable
fun Percentage.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f%%", this.toFloat() * 100.0)
}