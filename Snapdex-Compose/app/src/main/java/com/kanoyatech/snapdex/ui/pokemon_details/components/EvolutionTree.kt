package com.kanoyatech.snapdex.ui.pokemon_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Level
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.Icons
import com.kanoyatech.snapdex.theme.Poppins
import com.kanoyatech.snapdex.theme.components.MaterialText
import com.kanoyatech.snapdex.theme.snapdexDarkBlue2
import com.kanoyatech.snapdex.theme.snapdexWhite
import com.kanoyatech.snapdex.ui.EvolutionUi
import com.kanoyatech.snapdex.ui.TypeUi
import com.kanoyatech.snapdex.ui.components.BrushIcon
import com.kanoyatech.snapdex.ui.mappers.name
import com.kanoyatech.snapdex.ui.mappers.smallImageId

@Composable
fun EvolutionTree(
    evolutionUi: EvolutionUi,
    onPokemonClick: (PokemonId) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = EvolutionTreeColors.borderColor,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PokemonRow(
            pokemon = evolutionUi.startingPokemon,
            modifier = Modifier
                .clickable {
                    onPokemonClick(evolutionUi.startingPokemon.id)
                }
        )

        evolutionUi.evolutions.forEach { (level, pokemon) ->
            LevelRow(level)
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

@Composable
fun PokemonRow(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    val typeUi = TypeUi.fromType(pokemon.types.first())

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = EvolutionTreeColors.borderColor,
                shape = RoundedCornerShape(90.dp)
            )
            .height(74.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(95.dp)
                .clip(RoundedCornerShape(90.dp))
                .background(typeUi.color),
            contentAlignment = Alignment.Center
        ) {
            BrushIcon(
                imageVector = ImageVector.vectorResource(id = typeUi.image),
                contentDescription = null,
                brush = Brush.linearGradient(
                    colors = listOf(
                        snapdexWhite,
                        snapdexWhite.copy(alpha = 0.1f)
                    )
                ),
                modifier = Modifier
                    .size(65.dp)
            )

            Image(
                bitmap = ImageBitmap.imageResource(pokemon.smallImageId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-4).dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(vertical = 10.dp)
                .padding(end = 10.dp)
        ) {
            Text(
                text = pokemon.name,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF1A1A1A)
            )

            Text(
                text = stringResource(R.string.pokemon_number, pokemon.id),
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun LevelRow(
    level: Level,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.EvolutionArrow,
            contentDescription = null,
            tint = snapdexDarkBlue2
        )

        MaterialText(
            text = stringResource(R.string.level_x, level),
            color = snapdexDarkBlue2
        )
    }
}

object EvolutionTreeColors {
    val borderColor: Color @Composable get() = MaterialTheme.colorScheme.surface
}

@Preview(showBackground = true)
@Composable
private fun EvolutionTreePreview() {
    AppTheme {
        EvolutionTree(
            evolutionUi = EvolutionUi.fromEvolution(Evolution.find(4)),
            onPokemonClick = {}
        )
    }
}