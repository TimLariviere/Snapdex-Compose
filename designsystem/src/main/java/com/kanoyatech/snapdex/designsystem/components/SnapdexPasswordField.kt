package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexPasswordField(
    state: TextFieldState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicSecureTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(color = SnapdexTheme.colorScheme.onSurface),
        textObfuscationMode =
            if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        cursorBrush = SolidColor(SnapdexTheme.colorScheme.primary),
        modifier =
            modifier
                .height(44.dp)
                .clip(SnapdexTheme.shapes.regular)
                .background(SnapdexTheme.colorScheme.surfaceVariant)
                .border(
                    width = 1.dp,
                    color =
                        if (isFocused) {
                            SnapdexTheme.colorScheme.primary
                        } else {
                            SnapdexTheme.colorScheme.outline
                        },
                    shape = SnapdexTheme.shapes.regular,
                )
                .onFocusChanged { isFocused = it.isFocused },
        decorator = { innerBox ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = SnapdexTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    innerBox()
                }

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.EyeClosed else Icons.Eye,
                        contentDescription = null,
                        tint = SnapdexTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun SnapdexPasswordFieldPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexPasswordField(
                state = rememberTextFieldState(),
                isPasswordVisible = false,
                onTogglePasswordVisibility = {},
                hint = "Password",
                modifier = Modifier.fillMaxWidth().padding(4.dp),
            )
        }
    }
}
