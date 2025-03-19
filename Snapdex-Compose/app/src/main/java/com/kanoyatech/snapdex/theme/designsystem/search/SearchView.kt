package com.kanoyatech.snapdex.theme.designsystem.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.snapdexBlue400
import com.kanoyatech.snapdex.ui.TypeUi

@Composable
fun SearchView(
    state: SearchViewState,
    hint: String,
    onRemoveFilterClick: (PokemonType) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    MaterialTheme.colorScheme.outline
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .clickable { isFocused = true },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isFocused || state.text.text.isNotEmpty() || state.filter.isEmpty()) {
            SearchTextField(
                state = state.text,
                hint = hint,
                isFocused = isFocused,
                onFocusChanged = { /*isFocused = it.isFocused*/ }
            )
        }

        if ((isFocused || state.text.text.isNotEmpty()) && state.filter.isNotEmpty()) {
            HorizontalDivider()
        }

        if (state.filter.isNotEmpty()) {
            FilterField(
                filter = state.filter,
                onRemoveFilterClick = onRemoveFilterClick
            )
        }
    }
}

@Composable
private fun SearchTextField(
    state: TextFieldState,
    hint: String,
    isFocused: Boolean,
    onFocusChanged: (FocusState) -> Unit
) {
    BasicTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged(onFocusChanged),
        decorator = { innerBox ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    innerBox()
                }
            }
        }
    )
}

@Composable
private fun FilterField(
    filter: List<PokemonType>,
    onRemoveFilterClick: (PokemonType) -> Unit
) {
    val types = filter.map { TypeUi.fromType(it) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(24.dp)
                .padding(start = 2.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            types.forEachIndexed { index, type ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(snapdexBlue400)
                        .padding(start = 12.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
                        .clickable { onRemoveFilterClick(filter[index]) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = type.name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchViewPreview0() {
    AppTheme {
        SearchView(
            state = SearchViewState(),
            hint = "Search Pokémon...",
            onRemoveFilterClick = {},
            modifier = Modifier
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchViewPreview1() {
    AppTheme {
        SearchView(
            state = SearchViewState(
                text = TextFieldState(
                    initialText = "Hello"
                )
            ),
            hint = "Search Pokémon...",
            onRemoveFilterClick = {},
            modifier = Modifier
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchViewPreview2() {
    AppTheme {
        SearchView(
            state = SearchViewState(
                filter = listOf(
                    PokemonType.POISON,
                    PokemonType.BUG
                )
            ),
            hint = "Search Pokémon...",
            onRemoveFilterClick = {},
            modifier = Modifier
                .padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchViewPreview3() {
    AppTheme {
        SearchView(
            state = SearchViewState(
                text = TextFieldState(
                    initialText = "Hello"
                ),
                filter = listOf(
                    PokemonType.POISON,
                    PokemonType.BUG
                )
            ),
            hint = "Search Pokémon...",
            onRemoveFilterClick = {},
            modifier = Modifier
                .padding(24.dp)
        )
    }
}