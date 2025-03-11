package com.kanoyatech.snapdex.ui.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.ui.State
import com.kanoyatech.snapdex.ui.TypeUi
import com.kanoyatech.snapdex.ui.utils.mediumImageId
import com.kanoyatech.snapdex.ui.pokedex.components.SmallTypeBadge
import com.kanoyatech.snapdex.ui.utils.getLocale
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokedexScreenRoot(
    viewModel: PokedexViewModel = koinViewModel(),
    onPokemonClick: (PokemonId) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        val locale = context.getLocale()
        viewModel.setLocale(locale)
    }

    PokedexScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is PokedexAction.OnPokemonClick -> onPokemonClick(action.pokemonId)
            }

            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokedexScreen(
    state: PokedexState,
    onAction: (PokedexAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.pokedex),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {

                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_pokeball),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.pokedex)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {

                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_statistic),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.statistics)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.profile)
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        when (state.state) {
            State.LOADING ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(64.dp)
                    )
                }
            State.IDLE ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    items(state.pokemons, key = { it.id }) { pokemon ->
                        PokemonItem(
                            pokemon = pokemon,
                            modifier = Modifier
                                .clickable {
                                    onAction(PokedexAction.OnPokemonClick(pokemon.id))
                                }
                        )
                    }
                }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    val types = pokemon.types.map { TypeUi.fromType(it) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            types.forEach { type ->
                SmallTypeBadge(type)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(id = pokemon.mediumImageId),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Text(
                text = stringResource(id = R.string.pokemon_number, pokemon.id)
            )
        }
    }
}

@Composable
fun UnknownItem(
    id: PokemonId,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "?",
                fontSize = 32.sp
            )
        }

        Text(
            text = stringResource(id = R.string.pokemon_number, id)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokedexScreenPreview() {
    AppTheme {
        PokedexScreen(
            state = PokedexState(),
            onAction = {}
        )
    }
}