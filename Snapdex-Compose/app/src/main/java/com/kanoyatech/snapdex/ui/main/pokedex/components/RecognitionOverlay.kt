package com.kanoyatech.snapdex.ui.main.pokedex.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.SnapdexTheme
import com.kanoyatech.snapdex.theme.designsystem.GifImage
import com.kanoyatech.snapdex.theme.designsystem.SnapdexPrimaryButton
import com.kanoyatech.snapdex.theme.designsystem.SnapdexCircularProgressIndicator
import com.kanoyatech.snapdex.theme.designsystem.SnapdexText
import com.kanoyatech.snapdex.ui.utils.PokemonResourceProvider
import com.kanoyatech.snapdex.ui.utils.translated
import java.util.Locale

data class PokemonCaught(
    val id: Int,
    val name: Map<Locale, String>
)

@Composable
fun RecognitionOverlay(
    isRecognizing: Boolean,
    pokemon: PokemonCaught?,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            when {
                isRecognizing -> RecognitionInProgress()
                pokemon != null -> PokemonCaught(pokemon, onDismissRequest)
                else -> NothingCaught(onDismissRequest)
            }
        }
    }
}

@Composable
private fun RecognitionInProgress() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SnapdexCircularProgressIndicator(
            modifier = Modifier
                .size(80.dp)
        )
        SnapdexText(
            text = stringResource(id = R.string.recognizing)
        )
    }
}

@Composable
private fun PokemonCaught(
    pokemon: PokemonCaught,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
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
        SnapdexText(
            text = stringResource(id = R.string.congratulations),
            style = SnapdexTheme.typography.heading2
        )
        SnapdexText(
            text = stringResource(id = R.string.you_caught)
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
            SnapdexText(
                text = pokemon.name.translated(),
                style = SnapdexTheme.typography.heading1
            )
            SnapdexText(
                text = stringResource(id = R.string.pokemon_number, pokemon.id),
                style = SnapdexTheme.typography.smallLabel
            )
        }
        SnapdexPrimaryButton(
            text = "Awesome!",
            onClick = onDismissRequest
        )
    }
}

@Composable
private fun NothingCaught(onDismissRequest: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
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
        SnapdexText(
            text = stringResource(id = R.string.you_missed_it),
            style = SnapdexTheme.typography.heading2
        )
        SnapdexText(
            text = "?",
            style = SnapdexTheme.typography.heading1.copy(
                fontSize = 120.sp
            )
        )
        SnapdexText(
            text = stringResource(id = R.string.could_not_find_pokemon),
            textAlign = TextAlign.Center
        )
        SnapdexPrimaryButton(
            text = stringResource(id = R.string.try_again),
            onClick = onDismissRequest
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1080, locale = "en")
@Composable
private fun RecognitionOverlayPreview() {
    AppTheme {
        RecognitionOverlay(
            isRecognizing = false,
            pokemon = PokemonCaught(
                id = 6,
                name = mapOf(Locale.ENGLISH to "Charizard")
            ),
            onDismissRequest = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1080, locale = "en")
@Composable
private fun RecognitionOverlay2Preview() {
    AppTheme {
        RecognitionOverlay(
            isRecognizing = false,
            pokemon = null,
            onDismissRequest = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 1080, locale = "en")
@Composable
private fun RecognitionOverlay3Preview() {
    AppTheme {
        RecognitionOverlay(
            isRecognizing = true,
            pokemon = null,
            onDismissRequest = {}
        )
    }
}