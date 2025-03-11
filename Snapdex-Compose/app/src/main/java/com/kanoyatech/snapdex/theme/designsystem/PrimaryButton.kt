package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Poppins
import com.kanoyatech.snapdex.theme.snapdexGray100
import com.kanoyatech.snapdex.theme.snapdexGray400

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
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = snapdexGray100,
            disabledContentColor = snapdexGray400
        ),
        modifier = modifier
            .height(58.dp)
    ) {
        val contentColor = LocalContentColor.current
        Text(
            text = text,
            fontFamily = Poppins,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = contentColor
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

@Preview
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