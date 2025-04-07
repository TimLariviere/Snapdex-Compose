package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexSecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors()
    val buttonColors = ButtonDefaults.buttonColors()
    val colors = outlinedButtonColors.copy(
        contentColor = SnapdexTheme.colorScheme.primary,
        containerColor = SnapdexTheme.colorScheme.surface,
        disabledContainerColor = buttonColors.disabledContainerColor,
        disabledContentColor = buttonColors.disabledContentColor
    )

    val borderColor = if (enabled) {
        SnapdexTheme.colorScheme.primary
    } else {
        Color.Transparent
    }

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = SnapdexTheme.shapes.regular,
        colors = colors,
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        modifier = modifier
            .height(48.dp)
    ) {
        Text(
            text = text,
            style = SnapdexTheme.typography.paragraph
        )
    }
}

@Preview
@Composable
private fun SnapdexSecondaryButtonEnabledPreview() {
    AppTheme {
        SnapdexSecondaryButton(
            text = "Click me",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnapdexSecondaryButtonDisabledPreview() {
    AppTheme {
        SnapdexSecondaryButton(
            text = "Click me",
            enabled = false,
            onClick = {}
        )
    }
}