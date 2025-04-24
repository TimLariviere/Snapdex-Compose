package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.designsystem.AppTheme
import com.kanoyatech.snapdex.designsystem.SnapdexTheme
import com.kanoyatech.snapdex.designsystem.components.SnapdexBackground
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.ui.TypeUi

@Composable
fun TypeTag(typeUi: TypeUi, modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .clip(SnapdexTheme.shapes.small)
                .background(SnapdexTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = SnapdexTheme.colorScheme.outline,
                    shape = SnapdexTheme.shapes.small,
                )
                .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = typeUi.image),
            contentDescription = null,
            tint = typeUi.color,
            modifier = Modifier.height(20.dp).aspectRatio(1f),
        )

        Text(text = stringResource(id = typeUi.name))
    }
}

@Preview
@Composable
private fun TypeViewPreview() {
    AppTheme {
        SnapdexBackground(modifier = Modifier.height(IntrinsicSize.Max).width(IntrinsicSize.Max)) {
            TypeTag(
                typeUi = TypeUi.fromType(PokemonType.FIRE),
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
