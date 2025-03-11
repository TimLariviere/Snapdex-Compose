package com.kanoyatech.snapdex.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.components.MaterialText
import com.kanoyatech.snapdex.theme.snapdexLightRed

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
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = snapdexLightRed
        ),
        modifier = modifier
    ) {
        MaterialText(text = text)
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    AppTheme {
        PrimaryButton(text = "Click me") {

        }
    }
}