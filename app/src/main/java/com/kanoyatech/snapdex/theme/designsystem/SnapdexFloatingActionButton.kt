package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = SnapdexTheme.colorScheme.primary,
        contentColor = SnapdexTheme.colorScheme.onPrimary,
        modifier = modifier,
        content = content
    )
}

@Preview
@Composable
private fun SnapdexFloatingActionButtonPreview() {
    AppTheme {
        SnapdexBackground {
            SnapdexFloatingActionButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Pokeball,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}