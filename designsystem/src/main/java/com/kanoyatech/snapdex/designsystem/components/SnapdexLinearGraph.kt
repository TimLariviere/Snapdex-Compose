package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexLinearGraph(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val progress2 = progress.coerceAtLeast(0.01f)

    Row(
        modifier = modifier
            .clip(SnapdexTheme.shapes.small)
            .background(SnapdexTheme.colorScheme.statsFill)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
                .clip(RoundedCornerShape(26.dp - 4.dp))
                .background(color = SnapdexTheme.colorScheme.primary)
                .weight(progress2)
        )

        Spacer(modifier = Modifier.weight(1f - progress2))
    }
}

@PreviewLightDark
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