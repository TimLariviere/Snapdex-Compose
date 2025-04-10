package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexRadioButton(selected: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color =
                        if (selected) SnapdexTheme.colorScheme.primary
                        else SnapdexTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp),
                )
                .background(SnapdexTheme.colorScheme.surface)
    ) {
        if (selected) {
            Box(
                modifier =
                    Modifier.fillMaxSize()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(SnapdexTheme.colorScheme.primary)
            )
        }
    }
}

@Preview
@Composable
private fun SnapdexRadioButtonPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                SnapdexRadioButton(selected = true)
                SnapdexRadioButton(selected = false)
            }
        }
    }
}
