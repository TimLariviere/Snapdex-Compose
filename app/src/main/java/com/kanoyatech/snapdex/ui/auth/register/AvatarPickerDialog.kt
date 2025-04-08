package com.kanoyatech.snapdex.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.designsystem.components.SnapdexPrimaryButton
import com.kanoyatech.snapdex.ui.components.AvatarView

@OptIn(ExperimentalLayoutApi::class)
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
        CompositionLocalProvider(
            LocalContentColor provides SnapdexTheme.colorScheme.onBackground
        ) {
            Column(
                modifier = Modifier
                    .clip(SnapdexTheme.shapes.small)
                    .background(SnapdexTheme.colorScheme.surfaceVariant)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.pick_your_avatar),
                    style = SnapdexTheme.typography.heading3,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(10) { index ->
                        val isSelected = index == selectedIndex.intValue

                        AvatarView(
                            avatarId = index,
                            isSelected = isSelected,
                            modifier = Modifier
                                .size(90.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { selectedIndex.intValue = index }
                                )
                        )
                    }

                    Spacer(modifier = Modifier.weight(2f))
                }

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