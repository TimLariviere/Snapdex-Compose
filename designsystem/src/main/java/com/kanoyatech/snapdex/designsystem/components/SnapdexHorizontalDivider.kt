package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = SnapdexTheme.colorScheme.outline
    )
}