package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import com.kanoyatech.snapdex.theme.AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.theme.Icons

@Composable
fun SnapdexPasswordField(
    state: TextFieldState,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = ""
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    BasicSecureTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(
            color = Color(0xFF000000)
        ),
        textObfuscationMode = if (isPasswordVisible) {
            TextObfuscationMode.Visible
        } else {
            TextObfuscationMode.RevealLastTyped
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        cursorBrush = SolidColor(Color(0xFF000000)),
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    Color(0xFF000000)
                } else {
                    Color(0xFF999999)
                },
                shape = RoundedCornerShape(5.dp)
            )
            .onFocusChanged {
                isFocused = it.isFocused
            },
        decorator = { innerBox ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = Color(0xFF999999),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    innerBox()
                }

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(
                    onClick = onTogglePasswordVisibility
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.EyeClosed else Icons.Eye,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun SnapdexPasswordFieldPreview() {
    AppTheme {
        SnapdexPasswordField(
            state = rememberTextFieldState(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {},
            hint = "Password",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}