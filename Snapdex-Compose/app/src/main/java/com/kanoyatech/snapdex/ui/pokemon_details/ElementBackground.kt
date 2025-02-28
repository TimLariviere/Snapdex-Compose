package com.kanoyatech.snapdex.ui.pokemon_details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.ElementColor

data class ElementUi(
    @StringRes val name: Int,
    val color: Color,
    @DrawableRes val imageId: Int
) {
    companion object {
        val Normal = ElementUi(
            name = R.string.element_normal,
            color = ElementColor.Normal,
            imageId = R.drawable.normal
        )
        val Fire = ElementUi(
            name = R.string.element_fire,
            color = ElementColor.Fire,
            imageId = R.drawable.fire
        )
        val Water = ElementUi(
            name = R.string.element_water,
            ElementColor.Water,
            imageId = R.drawable.water
        )
        val Grass = ElementUi(
            name = R.string.element_grass,
            color = ElementColor.Grass,
            imageId = R.drawable.grass
        )
        val Electric = ElementUi(
            name = R.string.element_electric,
            color = ElementColor.Electric,
            imageId = R.drawable.electric
        )
        val Ice = ElementUi(
            name = R.string.element_ice,
            color = ElementColor.Ice,
            imageId = R.drawable.ice
        )
        val Fighting = ElementUi(
            name = R.string.element_fighting,
            color = ElementColor.Fighting,
            imageId = R.drawable.fighting
        )
        val Poison = ElementUi(
            name = R.string.element_poison,
            color = ElementColor.Poison,
            imageId = R.drawable.poison
        )
        val Ground = ElementUi(
            name = R.string.element_ground,
            color = ElementColor.Ground,
            imageId = R.drawable.ground
        )
        val Flying = ElementUi(
            name = R.string.element_flying,
            color = ElementColor.Flying,
            imageId = R.drawable.flying
        )
        val Psychic = ElementUi(
            name = R.string.element_psychic,
            color = ElementColor.Psychic,
            imageId = R.drawable.psychic
        )
        val Bug = ElementUi(
            name = R.string.element_bug,
            color = ElementColor.Bug,
            imageId = R.drawable.bug
        )
        val Rock = ElementUi(
            name = R.string.element_rock,
            color = ElementColor.Rock,
            imageId = R.drawable.rock
        )
        val Ghost = ElementUi(
            name = R.string.element_ghost,
            color = ElementColor.Ghost,
            imageId = R.drawable.ghost
        )
        val Dragon = ElementUi(
            name = R.string.element_dragon,
            color = ElementColor.Dragon,
            imageId = R.drawable.dragon
        )
        val Dark = ElementUi(
            name = R.string.element_dark,
            color = ElementColor.Dark,
            imageId = R.drawable.dark
        )
        val Steel = ElementUi(
            name = R.string.element_steel,
            color = ElementColor.Steel,
            imageId = R.drawable.steel
        )
        val Fairy = ElementUi(
            name = R.string.element_fairy,
            color = ElementColor.Fairy,
            imageId = R.drawable.fairy
        )
    }
}

@Composable
fun ElementBackground(
    element: ElementUi,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val painter = painterResource(id = element.imageId)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val radius = size.minDimension * 0.65f
            val center = Offset(x = size.width / 2.0f, y = 100.0f)

            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        element.color,
                        element.color.copy(alpha = 0.5f)
                    ),
                    start = Offset.Zero,
                    end = Offset(x = radius * 1.2f, y = radius * 1.2f)
                ),
                radius = radius,
                center = center
            )

            // Set up image size and position
            val imageWidth: Float
            val imageHeight: Float
            if (painter.intrinsicSize.width >= painter.intrinsicSize.height) {
                imageWidth = 200.dp.toPx()
                imageHeight = painter.intrinsicSize.height * (imageWidth / painter.intrinsicSize.width)
            } else {
                imageHeight = 200.dp.toPx()
                imageWidth = painter.intrinsicSize.width * (imageHeight / painter.intrinsicSize.height)
            }
            val imageSize = Size(imageWidth, imageHeight)

            val imageOffset = Offset(x = (size.width - imageSize.width) / 2.3f, y = 150.0f)

            // Draw the vector image with its own linear gradient
            with(painter) {
                // Use a SaveLayer operation to apply the gradient to the image
                drawContext.canvas.saveLayer(
                    Rect(
                        offset = imageOffset,
                        size = imageSize
                    ),
                    Paint()
                )

                // Draw the original image
                translate(left = imageOffset.x, top = imageOffset.y) {
                    draw(size = imageSize)
                }

                // Draw the gradient on top with BlendMode.SrcIn to only apply the gradient
                // to the non-transparent parts of the image
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White,
                            Color.Transparent
                        ),
                        start = Offset.Zero,
                        end = Offset(x = imageSize.width * 1.2f, y = imageSize.height * 1.2f)
                    ),
                    topLeft = imageOffset,
                    size = imageSize,
                    blendMode = BlendMode.SrcIn
                )

                drawContext.canvas.restore()
            }
        }

        content()
    }
}

@Preview
@Composable
private fun ElementBackgroundGrassPreview() {
    AppTheme {
        ElementBackground(
            element = ElementUi.Grass
        ) {

        }
    }
}

@Preview
@Composable
private fun ElementBackgroundFirePreview() {
    AppTheme {
        ElementBackground(
            element = ElementUi.Fire
        ) {

        }
    }
}