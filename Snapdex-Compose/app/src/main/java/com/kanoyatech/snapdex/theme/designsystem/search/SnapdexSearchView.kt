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
import androidx.compose.foundation.layout.defaultMinSize
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
import com.kanoyatech.snapdex.theme.LocalTextStyle
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.SnapdexBackground
import com.kanoyatech.snapdex.ui.TypeUi

@Composable
fun SnapdexSearchView(
    state: SnapdexSearchViewState,
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
            .defaultMinSize(minHeight = 44.dp)
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    SnapdexTheme.colorScheme.primary
                } else {
                    SnapdexTheme.colorScheme.outline
                },
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .background(SnapdexTheme.colorScheme.surface)
            .padding(horizontal = 16.dp)
            .clickable { isFocused = true },
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
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
            color = SnapdexTheme.colorScheme.onSurface
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(SnapdexTheme.colorScheme.primary),
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
                    tint = SnapdexTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (state.text.isEmpty() && !isFocused) {
                        Text(
                            text = hint,
                            color = SnapdexTheme.colorScheme.onSurfaceVariant,
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
            tint = SnapdexTheme.colorScheme.onSurfaceVariant,
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
                        .background(SnapdexTheme.colorScheme.primary)
                        .padding(start = 12.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
                        .clickable { onRemoveFilterClick(filter[index]) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = type.name),
                        color = SnapdexTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Close,
                        contentDescription = null,
                        tint = SnapdexTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SnapdexSearchViewPreview0() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexSearchView(
                state = SnapdexSearchViewState(),
                hint = "Search Pokémon...",
                onRemoveFilterClick = {},
                modifier = Modifier
                    .padding(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SnapdexSearchViewPreview1() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexSearchView(
                state = SnapdexSearchViewState(
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
}

@Preview(showBackground = true)
@Composable
private fun SnapdexSearchViewPreview2() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexSearchView(
                state = SnapdexSearchViewState(
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
}

@Preview
@Composable
private fun SnapdexSearchViewPreview3() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Min)) {
            SnapdexSearchView(
                state = SnapdexSearchViewState(
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
}