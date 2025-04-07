package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isBusy: Boolean = false,
    onClick: () -> Unit
) {
    val colors = ButtonColors(
        containerColor = SnapdexTheme.colorScheme.primary,
        contentColor = SnapdexTheme.colorScheme.onPrimary,
        disabledContainerColor = SnapdexTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = SnapdexTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    Button(
        onClick = onClick,
        enabled = enabled && !isBusy,
        shape = SnapdexTheme.shapes.regular,
        modifier = modifier
            .height(48.dp),
        colors = colors
    ) {
        if (isBusy) {
            SnapdexCircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
            )
        } else {
            Text(
                text,
                style = SnapdexTheme.typography.paragraph)
        }
    }
}

@Preview
@Composable
private fun SnapdexPrimaryButtonEnabledPreview() {
    AppTheme {
        SnapdexPrimaryButton(
            text = "Click me",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnapdexPrimaryButtonDisabledPreview() {
    AppTheme {
        SnapdexPrimaryButton(
            text = "Click me",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SnapdexPrimaryButtonBusyPreview() {
    AppTheme {
        SnapdexPrimaryButton(
            text = "Click me",
            isBusy = true,
            onClick = {}
        )
    }
}