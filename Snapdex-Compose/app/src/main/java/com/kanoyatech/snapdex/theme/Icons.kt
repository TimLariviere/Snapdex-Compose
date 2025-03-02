package com.kanoyatech.snapdex.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kanoyatech.snapdex.R

data object Icons {
    val ArrowBack = Icons.AutoMirrored.Default.KeyboardArrowLeft
    val Favorite = Icons.Default.Favorite
    val FavoriteBorder = Icons.Default.FavoriteBorder

    val Category: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_category)

    val Height: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_height)

    val Pokeball: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_pokeball)

    val Weight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.icon_weight)
}

data object ElementIcon {
    val Bug: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_bug)

    val Dark: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_dark)

    val Dragon: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_dragon)

    val Electric: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_electric)

    val Fairy: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_fairy)

    val Fighting: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_fighting)

    val Fire: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_fire)

    val Ghost: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_ghost)

    val Grass: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_grass)

    val Ground: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_ground)

    val Ice: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_ice)

    val Normal: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_normal)

    val Poison: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_poison)

    val Psychic: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_psychic)

    val Rock: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_rock)

    val Steel: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_steel)

    val Water: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.type_water)
}