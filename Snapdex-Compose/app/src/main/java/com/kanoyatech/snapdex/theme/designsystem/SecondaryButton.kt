package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
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
import com.kanoyatech.snapdex.theme.ButtonTextStyle

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
        ),
        border = BorderStroke(
            width = 2.dp,
            color = if (enabled) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
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