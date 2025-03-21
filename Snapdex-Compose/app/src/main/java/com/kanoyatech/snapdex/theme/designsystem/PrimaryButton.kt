package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    isDestructive: Boolean = false,
    enabled: Boolean = true,
    isBusy: Boolean = false,
    onClick: () -> Unit
) {
    val buttonColors = ButtonDefaults.buttonColors()
    val colors = buttonColors.copy(
        contentColor = if (isDestructive) {
            MaterialTheme.colorScheme.onError
        } else {
            buttonColors.contentColor
        },
        containerColor = if (isDestructive) {
            MaterialTheme.colorScheme.error
        } else {
            buttonColors.containerColor
        },
    )

    Button(
        onClick = onClick,
        enabled = enabled && !isBusy,
        modifier = modifier,
        colors = colors
    ) {
        if (isBusy) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
            )
        } else {
            Text(text)
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonEnabledPreview() {
    AppTheme {
        PrimaryButton(
            text = "Click me",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonDisabledPreview() {
    AppTheme {
        PrimaryButton(
            text = "Click me",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonBusyPreview() {
    AppTheme {
        PrimaryButton(
            text = "Click me",
            isBusy = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonDestructivePreview() {
    AppTheme {
        PrimaryButton(
            text = "Click me",
            isDestructive = true,
            onClick = {}
        )
    }
}