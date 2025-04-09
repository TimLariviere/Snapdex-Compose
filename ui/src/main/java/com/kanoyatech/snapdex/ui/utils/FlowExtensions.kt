package com.kanoyatech.snapdex.ui.utils

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow

fun TextFieldState.textAsFlow(): Flow<CharSequence> {
    return snapshotFlow { this.text }
}