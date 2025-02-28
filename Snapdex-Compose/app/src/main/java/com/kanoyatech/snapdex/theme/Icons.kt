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
        get() = ImageVector.vectorResource(id = R.drawable.category)

    val Height: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.height)

    val Pokeball: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.pokeball)

    val Weight: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.weight)
}

data object ElementIcon {
    val Bug: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.bug)

    val Dark: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.dark)

    val Dragon: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.dragon)

    val Electric: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.electric)

    val Fairy: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.fairy)

    val Fighting: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.fighting)

    val Fire: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.fire)

    val Ghost: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ghost)

    val Grass: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.grass)

    val Ground: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ground)

    val Ice: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ice)

    val Normal: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.normal)

    val Poison: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.poison)

    val Psychic: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.psychic)

    val Rock: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.rock)

    val Steel: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.steel)

    val Water: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.water)
}