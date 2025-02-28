package com.kanoyatech.snapdex.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.kanoyatech.snapdex.utils.Kg
import com.kanoyatech.snapdex.utils.Meters
import com.kanoyatech.snapdex.utils.Percentage

@Composable
fun Kg.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f kg", this.value)
}

@Composable
fun Meters.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f m", this.value)
}

@Composable
fun Percentage.formatted(): String {
    val locale = LocalConfiguration.current.getLocale()
    return String.format(locale, "%.1f%%", this.value)
}