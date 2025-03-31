package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexLinkButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (color == Color.Unspecified) {
            SnapdexTheme.colorScheme.onBackground
        } else {
            color
        },
        style = SnapdexTheme.typography.paragraph,
        textDecoration = TextDecoration.Underline,
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
    )
}

@Preview
@Composable
private fun SnapdexLinkButtonPreview() {
    AppTheme {
        SnapdexLinkButton(
            text = "Create an account",
            onClick = {}
        )
    }
}