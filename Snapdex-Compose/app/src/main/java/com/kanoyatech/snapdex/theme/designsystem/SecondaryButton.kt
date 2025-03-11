package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = snapdexGray100,
            disabledContentColor = snapdexGray400
        ),
        border = BorderStroke(
            width = if (enabled) 2.dp else 0.dp,
            color = MaterialTheme.colorScheme.primary
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
private fun SecondaryButtonEnabledPreview() {
    AppTheme {
        SecondaryButton(
            text = "Click me",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview
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