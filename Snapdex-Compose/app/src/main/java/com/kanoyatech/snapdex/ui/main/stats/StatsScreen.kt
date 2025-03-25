package com.kanoyatech.snapdex.ui.main.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.SnapdexBackground
import com.kanoyatech.snapdex.theme.designsystem.SnapdexCircleGraph
import com.kanoyatech.snapdex.theme.designsystem.SnapdexLinearGraph
import com.kanoyatech.snapdex.theme.designsystem.SnapdexText
import com.kanoyatech.snapdex.theme.pagePadding
import com.kanoyatech.snapdex.ui.TypeUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatsScreenRoot(
    paddingValues: PaddingValues,
    viewModel: StatsViewModel = koinViewModel()
) {
    StatsScreen(
        paddingValues = paddingValues,
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatsScreen(
    paddingValues: PaddingValues,
    state: StatsState,
    onAction: (StatsAction) -> Unit
) {
    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .pagePadding(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column {
                OverallProgress()
            }

            BoxWithConstraints {
                val maxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
                val itemWidth = (maxWidth - 16.dp) / 2

                FlowRow(
                    maxItemsInEachRow = 2,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    PokemonType.entries.forEach { type ->
                        TypeProgress(
                            type = type,
                            progress = 0.75f,
                            modifier = Modifier
                                .width(itemWidth)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OverallProgress() {
    Box(
        modifier = Modifier
            .clip(SnapdexTheme.shapes.regular)
            .background(SnapdexTheme.colorScheme.surface)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            SnapdexText(
                text = "Completion",
                style = SnapdexTheme.typography.heading3,
                textAlign = TextAlign.Center
            )
            SnapdexText(
                text = "75%",
                style = SnapdexTheme.typography.heading1,
                textAlign = TextAlign.Center
            )
            SnapdexLinearGraph(
                progress = 0.75f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
            SnapdexText(
                text = "113/151 pokemons captured",
                style = SnapdexTheme.typography.paragraph,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TypeProgress(type: PokemonType, progress: Float, modifier: Modifier = Modifier) {
    val typeUi = TypeUi.fromType(type)

    Box(
        modifier = modifier
            .clip(SnapdexTheme.shapes.regular)
            .background(SnapdexTheme.colorScheme.surface),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = typeUi.image),
                    contentDescription = null,
                    tint = typeUi.color,
                    modifier = Modifier
                        .size(24.dp)
                )

                SnapdexText(
                    text = stringResource(id = typeUi.name),
                    style = SnapdexTheme.typography.largeLabel
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
            ) {
                SnapdexCircleGraph(
                    progress = progress,
                    width = 24.dp,
                    modifier = Modifier
                        .fillMaxSize()
                )
                SnapdexText(
                    text = "75%",
                    style = SnapdexTheme.typography.heading3.copy(
                        fontSize = 28.sp
                    )
                )
            }

            SnapdexText(
                text = "113/151 captured",
                style = SnapdexTheme.typography.smallLabel
            )
        }
    }
}

@Preview
@Composable
private fun StatsScreenPreview() {
     AppTheme {
         SnapdexBackground {
             StatsScreen(
                 paddingValues = PaddingValues(0.dp),
                 state = StatsState(),
                 onAction = {}
             )
         }
    }
}