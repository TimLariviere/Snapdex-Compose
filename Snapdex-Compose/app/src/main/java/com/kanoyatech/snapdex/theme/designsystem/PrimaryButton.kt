package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.ButtonTextStyle

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .height(58.dp)
    ) {
        Text(
            text = text,
            style = ButtonTextStyle
        )
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