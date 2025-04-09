package com.kanoyatech.snapdex.ui.main.pokemon_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.LocalColors
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.ui.utils.formatted

@Composable
fun DataCardItem(
    icon: ImageVector,
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(SnapdexTheme.shapes.regular)
            .background(SnapdexTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = SnapdexTheme.colorScheme.outline,
                shape = SnapdexTheme.shapes.regular
            ),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides LocalColors.current.onSurface
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )

                    Text(
                        text = name.uppercase(),
                        style = SnapdexTheme.typography.smallLabel
                    )
                }

                Text(
                    text = value,
                    style = SnapdexTheme.typography.largeLabel
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DataCardItemPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Max)) {
            DataCardItem(
                icon = Icons.Weight,
                name = stringResource(id = R.string.weight),
                value = 100.0.kg.formatted(),
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}