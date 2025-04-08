package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = SnapdexTheme.colorScheme.primary
    )
}

@Preview
@Composable
private fun SnapdexCircularProgressIndicatorPreview() {
    AppTheme {
        SnapdexCircularProgressIndicator()
    }
}