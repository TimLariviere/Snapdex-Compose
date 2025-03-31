package com.kanoyatech.snapdex.ui.main.pokedex.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanoyatech.snapdex.domain.models.PokemonType
import com.kanoyatech.snapdex.theme.AppTheme
import com.kanoyatech.snapdex.theme.TypeColor
import com.kanoyatech.snapdex.ui.TypeUi

@Composable
fun SmallTypeBadge(
    typeUi: TypeUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(typeUi.color)
            .padding(4.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = typeUi.image),
            contentDescription = null,
            tint = TypeColor.OnType
        )
    }
}

@Preview
@Composable
private fun SmallTypeBadgePreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SmallTypeBadge(TypeUi.fromType(PokemonType.FIRE))
            SmallTypeBadge(TypeUi.fromType(PokemonType.WATER))
            SmallTypeBadge(TypeUi.fromType(PokemonType.ROCK))
            SmallTypeBadge(TypeUi.fromType(PokemonType.GRASS))
        }
    }
}