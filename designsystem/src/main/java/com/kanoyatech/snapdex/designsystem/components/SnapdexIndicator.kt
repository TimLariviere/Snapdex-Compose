package com.kanoyatech.snapdex.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme

@Composable
fun SnapdexIndicator(
    pageCount: Int,
    currentPage: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.wrapContentHeight().fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(pageCount) { iteration ->
            Box(
                modifier =
                    Modifier.clip(CircleShape)
                        .background(
                            if (currentPage == iteration) {
                                SnapdexTheme.colorScheme.primary
                            } else {
                                SnapdexTheme.colorScheme.surface
                            }
                        )
                        .border(
                            width = 1.dp,
                            color = SnapdexTheme.colorScheme.outline,
                            shape = CircleShape,
                        )
                        .size(16.dp)
                        .clickable { onClick(iteration) }
            )
        }
    }
}

@Preview
@Composable
private fun SnapdexIndicatorPreview() {
    AppTheme { SnapdexIndicator(pageCount = 3, currentPage = 0, onClick = {}) }
}
