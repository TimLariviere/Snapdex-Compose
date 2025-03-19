package com.kanoyatech.snapdex.ui.main.pokemon_detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.ui.TypeUi
import com.kanoyatech.snapdex.theme.designsystem.BrushIcon

@Composable
fun TypeBackground(
    type: TypeUi,
    modifier: Modifier = Modifier
) {
    // Header
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        // Draw the background circle
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Draw the background circle
            val radius = size.width * 0.65f
            val center = Offset(x = size.width / 2.0f, y = 100.0f)

            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        type.color,
                        type.color.copy(alpha = 0.5f)
                    ),
                    start = Offset.Zero,
                    end = Offset(x = radius * 1.2f, y = radius * 1.2f)
                ),
                radius = radius,
                center = center
            )
        }

        // Draw type icon
        BrushIcon(
            imageVector = ImageVector.vectorResource(id = type.image),
            contentDescription = null,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White,
                    Color.Transparent
                )
            ),
            modifier = Modifier
                .size(200.dp, 200.dp)
                .offset(y = 64.dp)
        )
    }
}

@Preview
@Composable
private fun ElementBackgroundGrassPreview() {
    AppTheme {
        TypeBackground(
            type = TypeUi.fromType(PokemonType.GRASS)
        )
    }
}

@Preview
@Composable
private fun ElementBackgroundFirePreview() {
    AppTheme {
        TypeBackground(
            type = TypeUi.fromType(PokemonType.FIRE)
        )
    }
}