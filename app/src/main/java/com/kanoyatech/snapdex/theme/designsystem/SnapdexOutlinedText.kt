package com.kanoyatech.snapdex.theme.designsystem

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.LocalColors

@Composable
fun SnapdexOutlinedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalColors.current.inOutline,
    outlineColor: Color = LocalColors.current.outline,
    textAlign: TextAlign? = null,
    style: TextStyle = LocalTextStyle.current
) {
    Box {
        Text(
            text = text,
            color = color,
            textAlign = textAlign,
            style = style,
            modifier = modifier
        )
        Text(
            text = text,
            color = outlineColor,
            textAlign = textAlign,
            style = style.copy(
                drawStyle = Stroke(
                    width = 1f
                )
            ),
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun SnapdexOutlinedTextPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)) {
            SnapdexOutlinedText(
                text = "Hello, world!",
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}