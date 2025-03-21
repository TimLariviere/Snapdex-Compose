package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme

@Composable
fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    val colorOverride =
        if (color == Color.Unspecified) {
            MaterialTheme.colorScheme.onBackground
        } else {
            color
        }

    Text(
        text = text,
        color = colorOverride,
        style = MaterialTheme.typography.labelLarge,
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clickable(enabled = enabled, onClick = onClick)
    )
}

@Preview
@Composable
private fun TextButtonPreview() {
    AppTheme {
        TextButton(
            text = "Create an account",
            onClick = {}
        )
    }
}