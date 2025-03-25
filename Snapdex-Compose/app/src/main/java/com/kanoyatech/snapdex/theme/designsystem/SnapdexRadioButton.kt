package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    RadioButton(
        selected = selected,
        onClick = null,
        modifier = modifier,
        colors = RadioButtonDefaults.colors(
            selectedColor = SnapdexTheme.colorScheme.primary,
            unselectedColor = SnapdexTheme.colorScheme.onSurface
        )
    )
}

@Preview
@Composable
private fun SnapdexRadioButtonPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)) {
            Column {
                SnapdexRadioButton(selected = true)
                SnapdexRadioButton(selected = false)
            }
        }
    }
}