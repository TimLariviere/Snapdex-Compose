package com.kanoyatech.snapdex.ui.auth.avatar_picker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.designsystem.PrimaryButton
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.AvatarUi

@Composable
fun AvatarPickerScreenRoot(
    selected: Int,
    onSelectedChange: (Int) -> Unit,
    onClose: () -> Unit
) {
    AvatarPickerScreen(
        selected = selected,
        onSelectionChange = onSelectedChange,
        onClose = onClose
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AvatarPickerScreen(
    selected: Int,
    onSelectionChange: (Int) -> Unit,
    onClose: () -> Unit
) {
    val selectedIndex = remember { mutableIntStateOf(selected) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.pick_an_avatar)
                    )
                },
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pagePadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) { index ->
                    val isSelected = index == selectedIndex.intValue

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .aspectRatio(1f)
                            .border(
                                width = if (isSelected) 4.dp else 1.dp,
                                color = if (isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outline
                                },
                                shape = CircleShape
                            )
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                selectedIndex.intValue = index
                            }
                    ) {
                        Image(
                            painter = painterResource(id = AvatarUi.getFor(index)),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }

            Text(
                text = stringResource(id = R.string.author_credit),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            PrimaryButton(
                text = stringResource(id = R.string.use_as_avatar),
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
private fun AvatarPickerScreenPreview() {
     AppTheme {
        AvatarPickerScreen(
            selected = -1,
            onSelectionChange = {},
            onClose = {}
        )
    }
}