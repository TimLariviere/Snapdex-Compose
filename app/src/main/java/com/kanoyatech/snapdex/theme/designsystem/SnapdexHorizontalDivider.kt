package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier,
        color = SnapdexTheme.colorScheme.outline
    )
}