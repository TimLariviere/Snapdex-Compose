@file:OptIn(ExperimentalLayoutApi::class)

package com.kanoyatech.snapdex.ui.pokemon_details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.Poppins
import com.kanoyatech.snapdex.theme.components.GifImage
import com.kanoyatech.snapdex.ui.components.SnapdexToolbar
import com.kanoyatech.snapdex.ui.utils.formatted
import com.kanoyatech.snapdex.utils.Kg
import com.kanoyatech.snapdex.utils.Meters
import com.kanoyatech.snapdex.utils.Percentage
import com.kanoyatech.snapdex.utils.kg
import com.kanoyatech.snapdex.utils.meters
import com.kanoyatech.snapdex.utils.percent

data class PokemonUi(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val imageId: Int,
    val elements: List<ElementUi>,
    val weaknesses: List<ElementUi>,
    val weight: Kg,
    val height: Meters,
    @StringRes val category: Int,
    @StringRes val abilities: Int,
    val maleToFemaleRatio: Percentage
) {
    companion object {
        val Venusaur = PokemonUi(
            id = 3,
            name = R.string.pokemon_003_name,
            description = R.string.pokemon_003_description,
            imageId = R.drawable.pokemon_003,
            elements = listOf(
                ElementUi.Grass,
                ElementUi.Poison
            ),
            weaknesses = listOf(
                ElementUi.Fire,
                ElementUi.Psychic,
                ElementUi.Flying,
                ElementUi.Ice
            ),
            weight = 100.0.kg,
            height = 2.0.meters,
            category = R.string.category_seed,
            abilities = R.string.abilities_overgrow,
            maleToFemaleRatio = 87.5.percent
        )

        val Charizard = PokemonUi(
            id = 6,
            name = R.string.pokemon_006_name,
            description = R.string.pokemon_006_description,
            imageId = R.drawable.pokemon_006,
            elements = listOf(
                ElementUi.Fire,
                ElementUi.Flying
            ),
            weaknesses = listOf(
                ElementUi.Water,
                ElementUi.Electric,
                ElementUi.Rock
            ),
            weight = 90.5.kg,
            height = 1.7.meters,
            category = R.string.category_flame,
            abilities = R.string.abilities_blaze,
            maleToFemaleRatio = 87.5.percent
        )
    }
}

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
        ElementBackground(
            element = pokemonUi.elements.first()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .systemBarsPadding()
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
                        .padding(top = 24.dp, bottom = 24.dp)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Details(pokemonUi = pokemonUi)
                    HorizontalDivider(color = Color(0xFFF2F2F2))
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
            imageId = pokemonUi.imageId,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(width = 275.dp, height = 200.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = pokemonUi.name),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = stringResource(R.string.pokemon_number, pokemonUi.id),
            style = MaterialTheme.typography.displayMedium,
            color = Color(0xFF444444)
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
            pokemonUi.elements.forEach { element ->
                ElementTag(element)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = pokemonUi.description),
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color(0xFF444444)
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
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.gender).uppercase(),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color(0xFF5B5B5B),
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
        Text(
            text = stringResource(id = R.string.weaknesses),
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )

        FlowRow(
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            pokemonUi.weaknesses.forEach { weakness ->
                ElementTag(
                    elementUi = weakness,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailsScreenPreview() {
     AppTheme {
        PokemonDetailsScreen(
            pokemonUi = PokemonUi.Charizard
        )
    }
}