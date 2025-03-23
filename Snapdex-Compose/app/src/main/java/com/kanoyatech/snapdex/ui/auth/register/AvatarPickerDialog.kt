package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.ui.components.AvatarView
import com.kanoyatech.snapdex.theme.designsystem.SnapdexBackground
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexText

@Composable
fun AvatarPickerDialog(
    selected: Int,
    onSelectionChange: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(selected) }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .height(420.dp)
                .clip(SnapdexTheme.shapes.regular)
                .background(SnapdexTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SnapdexText(
                text = stringResource(id = R.string.pick_an_avatar),
                style = SnapdexTheme.typography.heading3,
                color = SnapdexTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) { index ->
                    val isSelected = index == selectedIndex.intValue

                    AvatarView(
                        avatarId = index,
                        isSelected = isSelected,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                selectedIndex.intValue = index
                            }
                    )
                }
            }

            SnapdexText(
                text = stringResource(id = R.string.author_credit),
                style = SnapdexTheme.typography.smallLabel,
                color = SnapdexTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            SnapdexPrimaryButton(
                text = stringResource(id = R.string.use_avatar),
                enabled = selectedIndex.intValue > -1,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onSelectionChange(selectedIndex.intValue)
            }
        }
    }
}

@Preview
@Composable
private fun AvatarPickerDialogPreview() {
     AppTheme {
         SnapdexBackground {
             AvatarPickerDialog(
                 selected = -1,
                 onSelectionChange = {},
                 onDismissRequest = {}
             )
         }
    }
}