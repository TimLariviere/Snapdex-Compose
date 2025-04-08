package com.kanoyatech.snapdex.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.pagePadding(top: Dp = 20.dp): Modifier {
    return this.then(
        Modifier.padding(
            start = 16.dp,
            top = top,
            end = 16.dp,
            bottom = 20.dp
        )
    )
}