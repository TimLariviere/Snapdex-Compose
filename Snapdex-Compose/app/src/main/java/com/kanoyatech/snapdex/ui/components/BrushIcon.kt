package com.kanoyatech.snapdex.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme

@Composable
fun BrushIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
            .graphicsLayer {
                // Alpha compositing hack: setting to 1f doesn't work
                alpha = 0.99f
            }
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = brush,
                        blendMode = BlendMode.SrcIn
                    )
                }
            }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun BrushIconPreview() {
    AppTheme {
        BrushIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.type_fire),
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White,
                    Color.White.copy(alpha = 0.1f)
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
        )
    }
}