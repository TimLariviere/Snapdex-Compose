@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.ui.R
import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonAbility
import com.kanoyatech.snapdex.domain.models.PokemonCategory
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.units.Length
import com.kanoyatech.snapdex.domain.units.Percentage
import com.kanoyatech.snapdex.domain.units.Weight
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.Icons
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.GifImage
import com.kanoyatech.snapdex.designsystem.components.SnapdexCircularProgressIndicator
import com.kanoyatech.snapdex.designsystem.components.SnapdexScaffold
import com.kanoyatech.snapdex.designsystem.components.SnapdexTopAppBar
import com.kanoyatech.snapdex.ui.TypeUi
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.components.DataCardItem
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.components.EvolutionChainView
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.components.RatioBar
import com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.components.TypeTag
import com.kanoyatech.snapdex.ui.utils.formatted
import com.kanoyatech.snapdex.ui.utils.largeImageId
import com.kanoyatech.snapdex.ui.utils.translated
import java.util.Locale

@Composable
fun PokemonDetailScreenRoot(
    viewModel: PokemonDetailViewModel,
    onBackClick: () -> Unit,
    onPokemonClick: (PokemonId) -> Unit
) {
    PokemonDetailScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                PokemonDetailAction.OnBackClick -> onBackClick()
                is PokemonDetailAction.OnPokemonClick -> onPokemonClick(action.pokemonId)
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}

@Composable
private fun PokemonDetailScreen(
    state: PokemonDetailState,
    onAction: (PokemonDetailAction) -> Unit
) {
    SnapdexScaffold(
        topBar = {
            SnapdexTopAppBar(
                title = "",
                onBackClick = { onAction(PokemonDetailAction.OnBackClick) }
            )
        }
    ) { paddingValues ->
        if (state.pokemon == null) {
            SnapdexCircularProgressIndicator()
        }
        else {
            val types = state.pokemon.types.map { TypeUi.fromType(it) }
            val weaknesses = state.pokemon.weaknesses.map { TypeUi.fromType(it) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                GifImage(
                    imageId = state.pokemon.largeImageId,
                    modifier = Modifier
                        .height(180.dp)
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally)
                )

                Header(
                    id = state.pokemon.id,
                    name = state.pokemon.name.translated()
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    types.forEach { type ->
                        TypeTag(type)
                    }
                }

                Text(state.pokemon.description.translated())

                DataCardsSection(
                    weight = state.pokemon.weight,
                    height = state.pokemon.height,
                    category = state.pokemon.category,
                    ability = state.pokemon.ability,
                    maleToFemaleRatio = state.pokemon.maleToFemaleRatio
                )

                WeaknessesSection(
                    weaknesses = weaknesses
                )

                if (state.evolutionChain == null || state.evolutionChain.evolutions.isEmpty()) {
                    Box {}
                } else {
                    EvolutionChainSection(
                        evolutionChain = state.evolutionChain,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    id: PokemonId,
    name: String
) {
    Column {
        Text(
            text = stringResource(R.string.pokemon_number, id),
            style = SnapdexTheme.typography.smallLabel
        )
        Text(
            text = name,
            style = SnapdexTheme.typography.heading1
        )
    }
}

@Composable
private fun DataCardsSection(
    weight: Weight,
    height: Length,
    category: PokemonCategory,
    ability: PokemonAbility,
    maleToFemaleRatio: Percentage
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DataCardItem(
                icon = Icons.Weight,
                name = stringResource(id = R.string.weight),
                value = weight.formatted(),
                modifier = Modifier
                    .weight(1f)
            )

            DataCardItem(
                icon = Icons.Height,
                name = stringResource(id = R.string.height),
                value = height.formatted(),
                modifier = Modifier
                    .weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DataCardItem(
                icon = Icons.Category,
                name = stringResource(id = R.string.category),
                value = category.name.translated(),
                modifier = Modifier
                    .weight(1f)
            )

            DataCardItem(
                icon = Icons.Pokeball,
                name = stringResource(id = R.string.abilities),
                value = ability.name.translated(),
                modifier = Modifier
                    .weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.gender).uppercase(),
                style = SnapdexTheme.typography.largeLabel,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            RatioBar(
                ratio = maleToFemaleRatio
            )
        }
    }
}

@Composable
private fun WeaknessesSection(
    weaknesses: List<TypeUi>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.weaknesses).uppercase(),
            style = SnapdexTheme.typography.largeLabel
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            weaknesses.forEach { weakness ->
                TypeTag(weakness)
            }
        }
    }
}

@Composable
fun EvolutionChainSection(
    evolutionChain: EvolutionChain,
    onAction: (PokemonDetailAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy((-16).dp)
    ) {
        Text(
            text = stringResource(id = R.string.evolutions).uppercase(),
            style = SnapdexTheme.typography.largeLabel
        )

        EvolutionChainView(
            evolutionChain = evolutionChain,
            onPokemonClick = { pokemonId ->
                onAction(PokemonDetailAction.OnPokemonClick(pokemonId))
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun PokemonDetailsScreenPreview() {
    val pokemon = Pokemon(
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
    )

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
        PokemonDetailScreen(
            state = PokemonDetailState(
                pokemon = pokemon,
                evolutionChain = evolutionChain
            ),
            onAction = {}
        )
    }
}