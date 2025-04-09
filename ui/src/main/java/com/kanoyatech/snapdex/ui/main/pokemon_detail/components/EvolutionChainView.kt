package com.kanoyatech.snapdex.ui.main.pokemon_detail.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.Level
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonAbility
import com.kanoyatech.snapdex.domain.models.PokemonCategory
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.designsystem.components.SnapdexOutlinedText
import com.kanoyatech.snapdex.ui.utils.mediumImageId
import com.kanoyatech.snapdex.ui.utils.translated
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun EvolutionChainView(
    evolutionChain: EvolutionChain,
    onPokemonClick: (PokemonId) -> Unit,
    modifier: Modifier = Modifier
) {
    var animationStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            animationStep = (animationStep + 1) % (evolutionChain.evolutions.size * 2)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PokemonRow(
            pokemon = evolutionChain.startingPokemon,
            modifier = Modifier
                .clickable {
                    onPokemonClick(evolutionChain.startingPokemon.id)
                }
        )

        evolutionChain.evolutions.entries.forEachIndexed { index, (level, pokemon) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy((-24).dp)
            ) {
                val animateCurrentRow = animationStep / 2 == index
                val animateArrow1 = animateCurrentRow && (animationStep % 2 == 0)
                val animateArrow2 = animateCurrentRow && (animationStep % 2 == 1)

                LevelRow(
                    level = level,
                    animateArrow1 = animateArrow1,
                    animateArrow2 = animateArrow2
                )
                PokemonRow(
                    pokemon = pokemon,
                    modifier = Modifier
                        .clickable {
                            onPokemonClick(pokemon.id)
                        }
                )
            }
        }
    }
}

@Composable
fun PokemonRow(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Box {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(SnapdexTheme.shapes.regular)
                .background(SnapdexTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = SnapdexTheme.colorScheme.outline,
                    shape = SnapdexTheme.shapes.regular
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .height(76.dp)
                        .width(108.dp)
                )

                Column {
                    Text(
                        text = stringResource(R.string.pokemon_number, pokemon.id),
                        style = SnapdexTheme.typography.smallLabel
                    )

                    Text(
                        text = pokemon.name.translated(),
                        style = SnapdexTheme.typography.heading2
                    )
                }
            }
        }

        Image(
            bitmap = ImageBitmap.imageResource(pokemon.mediumImageId),
            contentDescription = null,
            modifier = Modifier
                .size(124.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun LevelRow(
    level: Level,
    animateArrow1: Boolean,
    animateArrow2: Boolean,
    modifier: Modifier = Modifier
) {
    val arrow1Tint by animateColorAsState(
        targetValue = if (animateArrow1) SnapdexTheme.colorScheme.primary.copy(alpha = 0.4f) else SnapdexTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 250, easing = EaseInOut),
        label = "Arrow1Tint"
    )
    val arrow2Tint by animateColorAsState(
        targetValue = if (animateArrow2) SnapdexTheme.colorScheme.primary.copy(alpha = 0.8f) else SnapdexTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 250, easing = EaseInOut),
        label = "Arrow2Tint"
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Icon(
                imageVector = Icons.ArrowDown,
                contentDescription = null,
                tint = arrow1Tint
            )
            Icon(
                imageVector = Icons.ArrowDown,
                contentDescription = null,
                tint = arrow2Tint
            )
        }

        SnapdexOutlinedText(
            text = stringResource(R.string.level_x, level),
            style = SnapdexTheme.typography.largeLabel
        )
    }
}

@Preview
@Composable
private fun EvolutionChainViewPreview() {
    val evolutionChain = EvolutionChain(
        startingPokemon = Pokemon(
            id = 6,
            name = mapOf(Locale.ENGLISH to "Charizard"),
            description = mapOf(Locale.ENGLISH to "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade."),
            types = listOf(
                PokemonType.FIRE,
                PokemonType.FLYING
            ),
            weaknesses = listOf(
                PokemonType.BUG
            ),
            weight = 120.0.kg,
            height = 1.70.m,
            category = PokemonCategory(id = 0, name = mapOf(Locale.ENGLISH to "Lizard")),
            ability = PokemonAbility(id = 0, name = mapOf(Locale.ENGLISH to "Blaze")),
            maleToFemaleRatio = 87.5.percent
        ),
        evolutions = mapOf(
            Pair(16, Pokemon(
                id = 6,
                name = mapOf(Locale.ENGLISH to "Charizard"),
                description = mapOf(Locale.ENGLISH to "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade."),
                types = listOf(
                    PokemonType.FIRE,
                    PokemonType.FLYING
                ),
                weaknesses = listOf(
                    PokemonType.BUG
                ),
                weight = 120.0.kg,
                height = 1.70.m,
                category = PokemonCategory(id = 0, name = mapOf(Locale.ENGLISH to "Lizard")),
                ability = PokemonAbility(id = 0, name = mapOf(Locale.ENGLISH to "Blaze")),
                maleToFemaleRatio = 87.5.percent
            )),
            Pair(32, Pokemon(
                id = 6,
                name = mapOf(Locale.ENGLISH to "Charizard"),
                description = mapOf(Locale.ENGLISH to "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade."),
                types = listOf(
                    PokemonType.FIRE,
                    PokemonType.FLYING
                ),
                weaknesses = listOf(
                    PokemonType.BUG
                ),
                weight = 120.0.kg,
                height = 1.70.m,
                category = PokemonCategory(id = 0, name = mapOf(Locale.ENGLISH to "Lizard")),
                ability = PokemonAbility(id = 0, name = mapOf(Locale.ENGLISH to "Blaze")),
                maleToFemaleRatio = 87.5.percent
            ))
        )
    )

    AppTheme {
        SnapdexBackground(modifier = Modifier
            .height(IntrinsicSize.Min)) {
            EvolutionChainView(
                evolutionChain = evolutionChain,
                onPokemonClick = {}
            )
        }
    }
}