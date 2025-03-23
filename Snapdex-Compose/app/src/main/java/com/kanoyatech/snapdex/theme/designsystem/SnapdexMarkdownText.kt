package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.LocalTextStyle
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun SnapdexMarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    MarkdownText(
        markdown = markdown,
        modifier = modifier,
        style = style
    )
}

@Preview
@Composable
private fun SnapdexMarkdownTextPreview() {
    AppTheme {
        SnapdexMarkdownText(
            markdown = "**Hello, world!**"
        )
    }
}