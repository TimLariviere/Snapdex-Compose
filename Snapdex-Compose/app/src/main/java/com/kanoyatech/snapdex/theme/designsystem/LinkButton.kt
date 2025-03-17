package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme

@Composable
fun LinkButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
        textDecoration = TextDecoration.Underline,
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clickable(enabled = enabled, onClick = onClick)
    )
}

@Preview
@Composable
private fun LinkButtonPreview() {
    AppTheme {
        LinkButton(
            text = "Create an account",
            onClick = {}
        )
    }
}