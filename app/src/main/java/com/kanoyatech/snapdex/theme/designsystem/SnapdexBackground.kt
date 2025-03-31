package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexBackground(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SnapdexTheme.colorScheme.background,
                        SnapdexTheme.colorScheme.backgroundVariant
                    )
                )
            ),
        content = content
    )
}

@Preview
@Composable
private fun GradientBackgroundPreview() {
    AppTheme {
        SnapdexBackground {}
    }
}