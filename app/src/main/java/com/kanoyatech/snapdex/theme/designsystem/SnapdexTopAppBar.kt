@file:OptIn(ExperimentalMaterial3Api::class)

package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexTopAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = SnapdexTheme.colorScheme.onBackground,
            titleContentColor = SnapdexTheme.colorScheme.onBackground,
            actionIconContentColor = SnapdexTheme.colorScheme.onBackground
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Preview
@Composable
private fun SnapdexTopAppBarPreview() {
    AppTheme {
        SnapdexTopAppBar(
            title = "Create an account",
            onBackClick = {}
        )
    }
}