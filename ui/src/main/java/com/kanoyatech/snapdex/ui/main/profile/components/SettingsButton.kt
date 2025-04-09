package com.kanoyatech.snapdex.ui.main.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SettingsPickerButton(
    text: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 20.dp)
        )

        Spacer(modifier = modifier.weight(1f))

        Text(
            text = value,
            style = SnapdexTheme.typography.smallLabel,
            color = SnapdexTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(end = 20.dp)
        )
    }
}

@Composable
fun SettingsButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 20.dp)
        )
    }
}

@Composable
fun DestructiveSettingsButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = SnapdexTheme.colorScheme.error,
            modifier = Modifier
                .padding(start = 20.dp)
        )
    }
}