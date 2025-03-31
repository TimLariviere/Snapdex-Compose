package com.kanoyatech.snapdex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.ui.AvatarUi

@Composable
fun AvatarView(
    avatarId: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .border(
                width = if (isSelected) 4.dp else 1.dp,
                color = if (isSelected) {
                    SnapdexTheme.colorScheme.primary
                } else {
                    SnapdexTheme.colorScheme.outline
                },
                shape = CircleShape
            )
            .background(SnapdexTheme.colorScheme.surface)

    ) {
        Image(
            painter = painterResource(id = AvatarUi.getFor(avatarId)),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun AvatarViewPreview() {
    AppTheme {
        AvatarView(
            avatarId = 1,
            isSelected = false
        )
    }
}

@Preview
@Composable
private fun AvatarViewSelectedPreview() {
    AppTheme {
        AvatarView(
            avatarId = 1,
            isSelected = true
        )
    }
}