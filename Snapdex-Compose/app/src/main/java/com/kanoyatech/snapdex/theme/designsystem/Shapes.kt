package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class Shapes(
    val regular: CornerBasedShape = RoundedCornerShape(10.dp)
)

val LocalShapes = staticCompositionLocalOf { Shapes() }