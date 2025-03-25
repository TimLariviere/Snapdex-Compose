package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexLinearGraph(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(SnapdexTheme.shapes.small)
            .background(SnapdexTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = SnapdexTheme.colorScheme.outline,
                shape = SnapdexTheme.shapes.small
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(progress)
                .background(color = SnapdexTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.weight(1f - progress))
    }
}

@Preview
@Composable
private fun SnapdexLinearGraphPreview() {
    AppTheme {
        SnapdexLinearGraph(
            progress = 0.75f,
            modifier = Modifier
                .height(32.dp)
        )
    }
}