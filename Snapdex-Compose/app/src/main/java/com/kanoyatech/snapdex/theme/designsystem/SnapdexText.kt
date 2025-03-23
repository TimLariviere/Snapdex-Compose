package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.LocalColors
import com.kanoyatech.snapdex.theme.LocalTextStyle

@Composable
fun SnapdexText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = LocalColors.current.onBackground,
    textAlign: TextAlign? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = style
    )
}

@Preview
@Composable
private fun SnapdexTextPreview() {
    AppTheme { 
        SnapdexText(
            text = "Hello, world!"
        )
    }
}