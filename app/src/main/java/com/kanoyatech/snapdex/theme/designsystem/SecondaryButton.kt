package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    val outlinedButtonColors = ButtonDefaults.outlinedButtonColors()
    val buttonColors = ButtonDefaults.buttonColors()
    val colors = outlinedButtonColors.copy(
        contentColor = if (isDestructive) {
            MaterialTheme.colorScheme.error
        } else {
            outlinedButtonColors.contentColor
        },
        disabledContainerColor = buttonColors.disabledContainerColor,
        disabledContentColor = buttonColors.disabledContentColor
    )

    val borderColor = when {
        enabled && isDestructive -> MaterialTheme.colorScheme.error
        enabled -> MaterialTheme.colorScheme.primary
        else -> Color.Transparent
    }

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50.dp),
        colors = colors,
        border = BorderStroke(
            width = 2.dp,
            color = borderColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text
        )
    }
}

@Preview
@Composable
private fun SecondaryButtonEnabledPreview() {
    AppTheme {
        SecondaryButton(
            text = "Click me",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonDisabledPreview() {
    AppTheme {
        SecondaryButton(
            text = "Click me",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun SecondaryButtonDestructivePreview() {
    AppTheme {
        SecondaryButton(
            text = "Click me",
            enabled = true,
            isDestructive = true,
            onClick = {}
        )
    }
}