package com.kanoyatech.snapdex.ui.utils

import android.content.Context
import java.util.Locale

fun Context.getLocale(): Locale {
    return this.resources.configuration.locales.get(0)
}
