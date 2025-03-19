@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.kanoyatech.snapdex.ui.main.pokemon_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.units.Length
import com.kanoyatech.snapdex.domain.units.Percentage
import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.PokemonAbility
import com.kanoyatech.snapdex.domain.models.PokemonCategory
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.domain.units.Weight
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.customColorScheme
import com.kanoyatech.snapdex.theme.designsystem.GifImage
import com.kanoyatech.snapdex.ui.TypeUi
import com.kanoyatech.snapdex.theme.designsystem.SnapdexToolbar
import com.kanoyatech.snapdex.ui.utils.formatted
import com.kanoyatech.snapdex.ui.main.pokemon_detail.components.TypeTag
import com.kanoyatech.snapdex.ui.main.pokemon_detail.components.DataCardItem
import com.kanoyatech.snapdex.ui.main.pokemon_detail.components.RatioBar
import com.kanoyatech.snapdex.ui.main.pokemon_detail.components.TypeBackground
import com.kanoyatech.snapdex.ui.utils.largeImageId
import com.kanoyatech.snapdex.ui.main.pokemon_detail.components.EvolutionChainView
import com.kanoyatech.snapdex.ui.utils.getLocale

@Composable
fun PokemonDetailScreenRoot(
    viewModel: PokemonDetailViewModel,
    onBackClick: () -> Unit,
    onPokemonClick: (PokemonId) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.setLocale(context.getLocale())
    }

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
    Scaffold(
        topBar = {
            SnapdexToolbar(
                isFavorite = state.isFavorite,
                onBackClick = {
                    onAction(PokemonDetailAction.OnBackClick)
                },
                onFavoriteClick = {
                    onAction(PokemonDetailAction.OnFavoriteToggleClick)
                }
            )
        }
    ) { paddingValues ->
        if (state.pokemon == null) {
            CircularProgressIndicator()
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
                Header(
                    id = state.pokemon.id,
                    name = state.pokemon.name,
                    image = state.pokemon.largeImageId,
                    type = types.first()
                )

                DescriptionSection(
                    description = state.pokemon.description,
                    types = types
                )

                HorizontalDivider()

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

                if (state.evolutionChain == null) {
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
    name: String,
    image: Int,
    type: TypeUi
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(250.dp)
        ) {
            TypeBackground(
                type = type
            )

            GifImage(
                imageId = image,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.customColorScheme.displayLarge
        )
        Text(
            text = stringResource(R.string.pokemon_number, id),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun DescriptionSection(
    description: String,
    types: List<TypeUi>
) {
    Column {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach { type ->
                TypeTag(type)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = description
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
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
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
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DataCardItem(
                icon = Icons.Category,
                name = stringResource(id = R.string.category),
                value = category.name,
                modifier = Modifier
                    .weight(1f)
            )

            DataCardItem(
                icon = Icons.Pokeball,
                name = stringResource(id = R.string.abilities),
                value = ability.name,
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
                style = MaterialTheme.typography.labelMedium,
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
            text = stringResource(id = R.string.weaknesses),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.customColorScheme.titleMedium
        )

        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            weaknesses.forEach { weakness ->
                TypeTag(
                    elementUi = weakness,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            // Add an empty box to avoid last type taking all the row
            if (weaknesses.count() % 2 == 1) {
                Box(modifier = Modifier.weight(1f))
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.evolutions),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.customColorScheme.titleMedium
        )

        EvolutionChainView(
            evolutionChain = evolutionChain,
            onPokemonClick = { pokemonId ->
                onAction(PokemonDetailAction.OnPokemonClick(pokemonId))
            }
        )
    }
}

@Preview
@Composable
private fun PokemonDetailsScreenPreview() {
    val pokemon = Pokemon(
        id = 6,
        name = "Charizard",
        description = "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
        types = listOf(
            PokemonType.FIRE,
            PokemonType.FLYING
        ),
        weaknesses = emptyList(),
        weight = 120.0.kg,
        height = 1.70.m,
        category = PokemonCategory(id = 0, name = "Lizard"),
        ability = PokemonAbility(id = 0, name = "Blaze"),
        maleToFemaleRatio = 87.5.percent
    )

    val evolutionChain = EvolutionChain(
        startingPokemon = Pokemon(
            id = 6,
            name = "Charizard",
            description = "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
            types = listOf(
                PokemonType.FIRE,
                PokemonType.FLYING
            ),
            weaknesses = emptyList(),
            weight = 120.0.kg,
            height = 1.70.m,
            category = PokemonCategory(id = 0, name = "Lizard"),
            ability = PokemonAbility(id = 0, name = "Blaze"),
            maleToFemaleRatio = 87.5.percent
        ),
        evolutions = mapOf(
            Pair(16, Pokemon(
                id = 6,
                name = "Charizard",
                description = "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
                types = listOf(
                    PokemonType.FIRE,
                    PokemonType.FLYING
                ),
                weaknesses = emptyList(),
                weight = 120.0.kg,
                height = 1.70.m,
                category = PokemonCategory(id = 0, name = "Lizard"),
                ability = PokemonAbility(id = 0, name = "Blaze"),
                maleToFemaleRatio = 87.5.percent
            )
            ),
            Pair(32, Pokemon(
                id = 6,
                name = "Charizard",
                description = "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
                types = listOf(
                    PokemonType.FIRE,
                    PokemonType.FLYING
                ),
                weaknesses = emptyList(),
                weight = 120.0.kg,
                height = 1.70.m,
                category = PokemonCategory(id = 0, name = "Lizard"),
                ability = PokemonAbility(id = 0, name = "Blaze"),
                maleToFemaleRatio = 87.5.percent
            )
            )
        )
    )

    AppTheme {
        PokemonDetailScreen(
            state = PokemonDetailState(
                pokemon = pokemon,
                evolutionChain = evolutionChain,
                isFavorite = false
            ),
            onAction = {}
        )
    }
}