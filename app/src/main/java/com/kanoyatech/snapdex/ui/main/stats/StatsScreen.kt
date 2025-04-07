package com.kanoyatech.snapdex.ui.main.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.models.Statistic
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.SnapdexBackground
import com.kanoyatech.snapdex.theme.designsystem.SnapdexCircleGraph
import com.kanoyatech.snapdex.theme.designsystem.SnapdexLinearGraph
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
            OverallProgress(state.overallCompletion)
            ProgressByType(state.completionByType)
        }
    }
}

@Composable
private fun OverallProgress(
    statistic: Statistic
) {
    val completionRate = statistic.caughtPokemonCount.toFloat() / statistic.totalPokemonCount
    val completionRateInt = (completionRate * 100).toInt()

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
            Text(
                text = stringResource(id = R.string.completion),
                style = SnapdexTheme.typography.heading3,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.percentage, completionRateInt),
                style = SnapdexTheme.typography.heading1,
                textAlign = TextAlign.Center
            )
            SnapdexLinearGraph(
                progress = completionRate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
            Text(
                text = stringResource(id = R.string.pokemons_captured, statistic.caughtPokemonCount, statistic.totalPokemonCount),
                style = SnapdexTheme.typography.paragraph,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun ProgressByType(statistics: Map<PokemonType, Statistic>) {
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
                    statistic = statistics[type]!!,
                    modifier = Modifier
                        .width(itemWidth)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
private fun TypeProgress(type: PokemonType, statistic: Statistic, modifier: Modifier = Modifier) {
    val typeUi = TypeUi.fromType(type)
    val completionRate = statistic.caughtPokemonCount.toFloat() / statistic.totalPokemonCount
    val completionRateInt = (completionRate * 100).toInt()

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

                Text(
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
                    progress = completionRate,
                    width = 24.dp,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Text(
                    text = stringResource(id = R.string.percentage, completionRateInt),
                    style = SnapdexTheme.typography.heading3.copy(
                        fontSize = 20.sp
                    )
                )
            }

            Text(
                text = stringResource(id = R.string.captured, statistic.caughtPokemonCount, statistic.totalPokemonCount),
                style = SnapdexTheme.typography.smallLabel
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun StatsScreenPreview() {
     AppTheme {
         SnapdexBackground {
             StatsScreen(
                 paddingValues = PaddingValues(0.dp),
                 state = StatsState(
                     completionByType = mapOf(
                         PokemonType.BUG to Statistic(totalPokemonCount = 151, caughtPokemonCount = 24),
                         PokemonType.DRAGON to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.ELECTRIC to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.FAIRY to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.FIGHTING to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.FIRE to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.FLYING to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.GHOST to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.GRASS to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.GROUND to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.ICE to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.NORMAL to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.POISON to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.PSYCHIC to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.ROCK to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.STEEL to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                         PokemonType.WATER to Statistic(totalPokemonCount = 151, caughtPokemonCount = 151),
                     )
                 ),
                 onAction = {}
             )
         }
    }
}