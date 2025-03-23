package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.SnapdexTheme

@Composable
fun SnapdexNavBar(
    tabs: Array<TabItem>,
    shouldShowNavBar: () -> Boolean,
    modifier: Modifier = Modifier
) {
    val selectedTab = remember { mutableIntStateOf(0) }
    val startWeight: Float by animateFloatAsState(selectedTab.intValue.toFloat(), label = "startWeight")
    val endWeight: Float by animateFloatAsState((tabs.size - 1).toFloat() - selectedTab.intValue, label = "startWeight")

    AnimatedVisibility(
        modifier = modifier,
        visible = shouldShowNavBar(),
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
                .clip(RoundedCornerShape(32.dp))
                .background(SnapdexTheme.colorScheme.surfaceContainer.copy(alpha = 0.6f))
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
                        .background(SnapdexTheme.colorScheme.primary)
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
                tabs.forEachIndexed { index, tab ->
                    SnapdexTabItem(
                        imageVector = tab.imageVector,
                        selected = index == selectedTab.intValue
                    ) {
                        selectedTab.intValue = index
                        tab.onClick()
                    }
                }
            }
        }
    }
}

@Composable
private fun SnapdexTabItem(
    imageVector: ImageVector,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint = if (selected) SnapdexTheme.colorScheme.surface else SnapdexTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .size(32.dp)
            .clickable {
                onClick()
            }
    )
}

data class TabItem(
    val imageVector: ImageVector,
    val onClick: () -> Unit
)

@Preview
@Composable
private fun SnapdexNavBarPreview() {
    AppTheme {
        SnapdexNavBar(
            tabs = arrayOf(
                TabItem(
                    imageVector = Icons.Pokeball,
                    onClick = {}
                ),
                TabItem(
                    imageVector = Icons.Statistics,
                    onClick = {}
                ),
                TabItem(
                    imageVector = Icons.Profile,
                    onClick = {}
                )
            ),
            shouldShowNavBar = { true }
        )
    }
}