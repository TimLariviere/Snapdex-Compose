package com.kanoyatech.snapdex.ui

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(@StringRes val id: Int, val args: Array<Any> = arrayOf()) : UiText

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}
