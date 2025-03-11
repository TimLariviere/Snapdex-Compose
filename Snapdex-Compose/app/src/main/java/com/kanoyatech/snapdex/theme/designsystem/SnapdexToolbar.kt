@file:OptIn(ExperimentalMaterial3Api::class)

package com.kanoyatech.snapdex.theme.designsystem

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons

@Composable
fun SnapdexToolbar(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.ArrowBack,
                    contentDescription = stringResource(id = R.string.go_back),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(38.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Favorite else Icons.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.go_back),
                    tint = if (!isFavorite) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        Color.Red
                    },
                    modifier = Modifier
                        .size(38.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF63BC5A)
@Composable
private fun SnapdexToolbarScreenPreview1() {
     AppTheme {
        SnapdexToolbar(
            isFavorite = false,
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF63BC5A)
@Composable
private fun SnapdexToolbarScreenPreview2() {
    AppTheme {
        SnapdexToolbar(
            isFavorite = true,
            onBackClick = {},
            onFavoriteClick = {}
        )
    }
}