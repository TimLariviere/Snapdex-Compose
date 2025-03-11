package com.kanoyatech.snapdex.ui.main.pokemon_details.components

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
import com.kanoyatech.snapdex.domain.PokemonType
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

//@Composable
//fun TypeBackground(
//    type: TypeUi,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        val painter = painterResource(id = type.image)
//        Canvas(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            val radius = size.minDimension * 0.65f
//            val center = Offset(x = size.width / 2.0f, y = 100.0f)
//
//            drawCircle(
//                brush = Brush.linearGradient(
//                    colors = listOf(
//                        type.color,
//                        type.color.copy(alpha = 0.5f)
//                    ),
//                    start = Offset.Zero,
//                    end = Offset(x = radius * 1.2f, y = radius * 1.2f)
//                ),
//                radius = radius,
//                center = center
//            )
//
//            // Set up image size and position
//            val imageWidth: Float
//            val imageHeight: Float
//            if (painter.intrinsicSize.width >= painter.intrinsicSize.height) {
//                imageWidth = 200.dp.toPx()
//                imageHeight = painter.intrinsicSize.height * (imageWidth / painter.intrinsicSize.width)
//            } else {
//                imageHeight = 200.dp.toPx()
//                imageWidth = painter.intrinsicSize.width * (imageHeight / painter.intrinsicSize.height)
//            }
//            val imageSize = Size(imageWidth, imageHeight)
//
//            val imageOffset = Offset(x = (size.width - imageSize.width) / 2.3f, y = 150.0f)
//
//            // Draw the vector image with its own linear gradient
//            with(painter) {
//                // Use a SaveLayer operation to apply the gradient to the image
//                drawContext.canvas.saveLayer(
//                    Rect(
//                        offset = imageOffset,
//                        size = imageSize
//                    ),
//                    Paint()
//                )
//
//                // Draw the original image
//                translate(left = imageOffset.x, top = imageOffset.y) {
//                    draw(size = imageSize)
//                }
//
//                // Draw the gradient on top with BlendMode.SrcIn to only apply the gradient
//                // to the non-transparent parts of the image
//                drawRect(
//                    brush = Brush.linearGradient(
//                        colors = listOf(
//                            Color.White,
//                            Color.Transparent
//                        ),
//                        start = Offset.Zero,
//                        end = Offset(x = imageSize.width * 1.2f, y = imageSize.height * 1.2f)
//                    ),
//                    topLeft = imageOffset,
//                    size = imageSize,
//                    blendMode = BlendMode.SrcIn
//                )
//
//                drawContext.canvas.restore()
//            }
//        }
//
//        content()
//    }
//}

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