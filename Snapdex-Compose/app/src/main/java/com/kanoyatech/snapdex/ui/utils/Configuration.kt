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
fun Configuration.getLocale(): Locale {
    return ConfigurationCompat.getLocales(this).get(0) ?: LocaleListCompat.getDefault()[0]!!
}