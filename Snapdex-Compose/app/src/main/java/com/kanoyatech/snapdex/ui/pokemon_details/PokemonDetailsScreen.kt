@file:OptIn(ExperimentalLayoutApi::class)

package com.kanoyatech.snapdex.ui.pokemon_details

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.Poppins
import com.kanoyatech.snapdex.theme.components.GifImage
import com.kanoyatech.snapdex.theme.components.MaterialHorizontalDivider
import com.kanoyatech.snapdex.theme.components.MaterialText
import com.kanoyatech.snapdex.ui.PokemonUi
import com.kanoyatech.snapdex.ui.components.SnapdexToolbar
import com.kanoyatech.snapdex.ui.utils.formatted
import com.kanoyatech.snapdex.ui.components.TypeTag

@Composable
fun PokemonDetailsScreenRoot(
    pokemonUi: PokemonUi
) {
    PokemonDetailsScreen(pokemonUi)
}

@Composable
private fun PokemonDetailsScreen(
    pokemonUi: PokemonUi
) {
    var isFavorite by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            SnapdexToolbar(
                isFavorite = isFavorite,
                onBackClick = {},
                onFavoriteClick = {
                    isFavorite = !isFavorite
                }
            )
        }
    ) { paddingValues ->
        TypeBackground(
            type = pokemonUi.type.first()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Header(
                    pokemonUi = pokemonUi,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Details(pokemonUi = pokemonUi)
                    MaterialHorizontalDivider()
                    InfoCards(pokemonUi = pokemonUi)
                    Weaknesses(pokemonUi = pokemonUi)
                }
            }
        }
    }
}

@Composable
private fun Header(
    pokemonUi: PokemonUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()) {
        GifImage(
            imageId = pokemonUi.bigImage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        MaterialText(
            text = stringResource(id = pokemonUi.name),
            style = MaterialTheme.typography.headlineMedium
        )
        MaterialText(
            text = stringResource(R.string.pokemon_number, pokemonUi.id),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Details(
    pokemonUi: PokemonUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemonUi.type.forEach { type ->
                TypeTag(type)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        MaterialText(
            text = stringResource(id = pokemonUi.description)
        )
    }
}

@Composable
private fun InfoCards(
    pokemonUi: PokemonUi,
    modifier: Modifier = Modifier
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
                value = pokemonUi.weight.formatted(),
                modifier = modifier
                    .weight(1f)
            )

            DataCardItem(
                icon = Icons.Height,
                name = stringResource(id = R.string.height),
                value = pokemonUi.height.formatted(),
                modifier = modifier
                    .weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DataCardItem(
                icon = Icons.Category,
                name = stringResource(id = R.string.category),
                value = stringResource(id = pokemonUi.category),
                modifier = modifier
                    .weight(1f)
            )

            DataCardItem(
                icon = Icons.Pokeball,
                name = stringResource(id = R.string.abilities),
                value = stringResource(id = pokemonUi.abilities),
                modifier = modifier
                    .weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MaterialText(
                text = stringResource(id = R.string.gender).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            RatioBar(
                ratio = pokemonUi.maleToFemaleRatio
            )
        }
    }
}

@Composable
private fun Weaknesses(
    pokemonUi: PokemonUi
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MaterialText(
            text = stringResource(id = R.string.weaknesses),
            style = MaterialTheme.typography.titleMedium
        )

        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            pokemonUi.weakness.forEach { weakness ->
                TypeTag(
                    elementUi = weakness,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            // Add an empty box to avoid last type taking all the row
            if (pokemonUi.weakness.count() % 2 == 1) {
                Box(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailsScreenPreview() {
    val context = LocalContext.current
     AppTheme {
        PokemonDetailsScreen(
            pokemonUi = PokemonUi.fromPokemon(context, Pokemon.Charizard)
        )
    }
}