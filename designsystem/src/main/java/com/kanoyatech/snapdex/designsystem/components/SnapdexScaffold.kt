package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        contentColor = SnapdexTheme.colorScheme.onBackground
    ) { paddingValues ->
        SnapdexBackground {
            content(paddingValues)
        }
    }
}

@Preview
@Composable
private fun SnapdexScaffoldPreview() {
    AppTheme {
        SnapdexScaffold {
            Text(text = "Hello")
        }
    }
}