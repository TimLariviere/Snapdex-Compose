package com.kanoyatech.snapdex.theme.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun MaterialText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign = TextAlign.Unspecified,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val colorOverride =
        if (color == Color.Unspecified) {
            style.color
        } else {
            color
        }

    Text(
        text = text,
        modifier = modifier,
        color = colorOverride,
        fontSize = fontSize,
        textAlign = textAlign,
        style = style
    )
}