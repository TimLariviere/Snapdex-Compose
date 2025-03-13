package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.snapdexGray700
import com.kanoyatech.snapdex.theme.snapdexRed200

@Composable
fun SnapdexNavBar(modifier: Modifier = Modifier) {
    val selectedTab = remember { mutableIntStateOf(0) }

    val startWeight = selectedTab.intValue.toFloat()
    val endWeight = 2f - selectedTab.intValue

    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
            .clip(RoundedCornerShape(32.dp))
            .background(snapdexGray700)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            if (startWeight > 0.0f) {
                Spacer(modifier = Modifier.weight(startWeight))
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(snapdexRed200)
            )

            if (endWeight > 0.0f) {
                Spacer(modifier = Modifier.weight(endWeight))
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TabItem(
                imageVector = Icons.Pokeball,
                selected = selectedTab.intValue == 0
            ) {
                selectedTab.intValue = 0
            }
            TabItem(
                imageVector = Icons.Statistics,
                selected = selectedTab.intValue == 1
            ) {
                selectedTab.intValue = 1
            }
            TabItem(
                imageVector = Icons.Profile,
                selected = selectedTab.intValue == 2
            ) {
                selectedTab.intValue = 2
            }
        }
    }
}

@Composable
fun TabItem(
    imageVector: ImageVector,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .size(32.dp)
            .clickable {
                onClick()
            }
    )
}

@Preview
@Composable
private fun SnapdexNavBarPreview() {
    AppTheme {
        SnapdexNavBar()
    }
}