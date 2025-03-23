package com.kanoyatech.snapdex.ui.main.pokedex.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.designsystem.GifImage
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.ui.main.pokedex.PokemonCaught
import com.kanoyatech.snapdex.ui.utils.PokemonResourceProvider

@Composable
fun PokemonCaughtOverlay(
    pokemon: PokemonCaught,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        )
                    )
                )
        ) {
            Text(
                text = stringResource(id = R.string.congratulations),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.you_caught),
                color = Color.White
            )
            GifImage(
                imageId = PokemonResourceProvider.getPokemonLargeImageId(context, pokemon.id),
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pokemon.name,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
                Text(
                    text = stringResource(id = R.string.pokemon_number, pokemon.id),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.LightGray
                )
            }
            SnapdexPrimaryButton(
                text = "Awesome!",
                onClick = onDismissRequest
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1080)
@Composable
private fun PokemonCaughtOverlayPreview() {
    AppTheme {
        PokemonCaughtOverlay(
            pokemon = PokemonCaught(
                id = 6,
                name = "Charizard"
            ),
            onDismissRequest = {}
        )
    }
}