package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(color = SnapdexTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(SnapdexTheme.colorScheme.primary),
        modifier =
            modifier
                .widthIn(max = 460.dp)
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .onFocusChanged { isFocused = it.isFocused },
        decorator = { innerBox ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = SnapdexTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    innerBox()
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun SnapdexTextFieldPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexTextField(
                state = rememberTextFieldState(),
                hint = "Email",
                modifier = Modifier.fillMaxWidth().padding(4.dp),
            )
        }
    }
}
