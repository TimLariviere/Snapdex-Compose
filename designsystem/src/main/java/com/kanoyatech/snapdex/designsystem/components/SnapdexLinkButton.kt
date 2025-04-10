package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexLinkButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        textDecoration = TextDecoration.Underline,
        color = SnapdexTheme.colorScheme.primary,
        modifier = modifier.clickable(enabled = enabled, onClick = onClick),
    )
}

@Preview
@Composable
private fun SnapdexLinkButtonPreview() {
    AppTheme { SnapdexLinkButton(text = "Create an account", onClick = {}) }
}
