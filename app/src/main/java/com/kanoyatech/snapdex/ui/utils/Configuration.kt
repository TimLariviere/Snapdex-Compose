package com.kanoyatech.snapdex.ui.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import java.util.Locale

@Composable
@ReadOnlyComposable
fun Configuration.getGlobalLocale(): Locale {
    var locale = ConfigurationCompat.getLocales(this).get(0) ?: LocaleListCompat.getDefault()[0]!!
    return Locale.forLanguageTag(locale.language)
}

@Composable
fun Map<Locale, String>.translated(): String {
    val locale = LocalConfiguration.current.getGlobalLocale()
    return this.getOrElse(locale, { "TRANSLATION MISSING" })
}