package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.LocalColors

@Composable
fun SnapdexOutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalColors.current.inOutline,
    outlineColor: Color = LocalColors.current.outline,
    textAlign: TextAlign? = null,
    style: TextStyle = LocalTextStyle.current,
    outlineWidth: Float = 1f,
) {
    Box {
        Text(text = text, color = color, textAlign = textAlign, style = style, modifier = modifier)
        Text(
            text = text,
            color = outlineColor,
            textAlign = textAlign,
            style = style.copy(drawStyle = Stroke(width = outlineWidth)),
            modifier = modifier,
        )
    }
}

@PreviewLightDark
@Composable
private fun SnapdexOutlinedTextPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)) {
            SnapdexOutlinedText(text = "Hello, world!", modifier = Modifier.padding(8.dp))
        }
    }
}
