package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexCircleGraph(
    progress: Float,
    modifier: Modifier = Modifier,
    width: Dp = 32.dp,
    borderWidth: Dp = 4.dp
) {
    val strokeWidth = width - borderWidth

    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }
    val borderPx = with(LocalDensity.current) { borderWidth.toPx() }

    val fillColor = SnapdexTheme.colorScheme.primary
    val outlineColor = SnapdexTheme.colorScheme.statsFill

    Canvas(modifier = modifier) {
        val diameter = size.minDimension
        val radius = diameter / 2f

        // Bounds
        val rect = Rect(
            left = ((size.width / 2f) - radius) + (strokePx + borderPx) / 2f,
            top = ((size.height / 2f) - radius) + (strokePx + borderPx) / 2f,
            right = ((size.width / 2f) + radius) - ((strokePx + borderPx) / 2f),
            bottom = ((size.height / 2f) + radius) - ((strokePx + borderPx) / 2f),
        )

        // Draw outline
        drawArc(
            color = outlineColor,
            startAngle = 180f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = rect.topLeft,
            size = rect.size,
            style = Stroke(
                width = strokePx + borderPx,
                cap = StrokeCap.Round
            )
        )

        // Draw fill
        drawArc(
            color = fillColor,
            startAngle = 180f,
            sweepAngle = 360f * progress,
            useCenter = false,
            topLeft = rect.topLeft,
            size = rect.size,
            style = Stroke(
                width = strokePx - borderPx,
                cap = StrokeCap.Round
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun SnapdexArcGraphPreview() {
    AppTheme {
        SnapdexBackground {
            SnapdexCircleGraph(
                progress = 0.35f,
                modifier = Modifier
                    .height(360.dp)
                    .width(240.dp)
            )
        }
    }
}