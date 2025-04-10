package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexNavBar(
    tabs: Array<TabItem>,
    shouldShowNavBar: Boolean,
    selectedTab: Int,
    modifier: Modifier = Modifier,
) {
    val startWeight: Float by animateFloatAsState(selectedTab.toFloat(), label = "startWeight")
    val endWeight: Float by
        animateFloatAsState((tabs.size - 1).toFloat() - selectedTab, label = "startWeight")

    AnimatedVisibility(
        modifier = modifier,
        visible = shouldShowNavBar,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
    ) {
        Box(
            modifier =
                Modifier.height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min)
                    .clip(SnapdexTheme.shapes.navBar)
                    .background(SnapdexTheme.colorScheme.navBarBackground)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    )
        ) {
            Row(
                modifier = Modifier.fillMaxHeight().padding(horizontal = 36.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                if (startWeight > 0.0f) {
                    Spacer(modifier = Modifier.weight(startWeight))
                }

                Box(
                    modifier =
                        Modifier.height(4.dp)
                            .width(40.dp)
                            .clip(SnapdexTheme.shapes.navBarIndicator)
                            .background(SnapdexTheme.colorScheme.primary)
                )

                if (endWeight > 0.0f) {
                    Spacer(modifier = Modifier.weight(endWeight))
                }
            }

            Row(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                tabs.forEachIndexed { index, tab ->
                    val isSelected = index == selectedTab
                    SnapdexTabItem(
                        imageVector = if (isSelected) tab.selectedImage else tab.unselectedImage,
                        selected = isSelected,
                        onClick = { tab.onClick() },
                    )
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
    onClick: () -> Unit,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        tint =
            if (selected) SnapdexTheme.colorScheme.primary
            else SnapdexTheme.colorScheme.navBarOnBackground,
        modifier =
            modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ),
    )
}

data class TabItem(
    val selectedImage: ImageVector,
    val unselectedImage: ImageVector,
    val onClick: () -> Unit,
)

@PreviewLightDark
@Composable
private fun SnapdexNavBarPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexNavBar(
                tabs =
                    arrayOf(
                        TabItem(
                            selectedImage = Icons.GridSelected,
                            unselectedImage = Icons.GridUnselected,
                            onClick = {},
                        ),
                        TabItem(
                            selectedImage = Icons.StatsSelected,
                            unselectedImage = Icons.StatsUnselected,
                            onClick = {},
                        ),
                        TabItem(
                            selectedImage = Icons.ProfileSelected,
                            unselectedImage = Icons.ProfileUnselected,
                            onClick = {},
                        ),
                    ),
                shouldShowNavBar = true,
                selectedTab = 1,
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
            )
        }
    }
}
