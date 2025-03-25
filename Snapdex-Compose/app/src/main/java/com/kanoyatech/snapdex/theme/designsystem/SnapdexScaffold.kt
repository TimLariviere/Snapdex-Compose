package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar
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
            SnapdexText(text = "Hello")
        }
    }
}