package com.kanoyatech.snapdex.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kanoyatech.snapdex.domain.models.Pokemon

val Pokemon.largeImageId: Int
    @Composable
    @DrawableRes
    get() = PokemonResourceProvider.getPokemonLargeImageId(LocalContext.current, this.id)

val Pokemon.mediumImageId: Int
    @Composable
    @DrawableRes
    get() = PokemonResourceProvider.getPokemonMediumImageId(LocalContext.current, this.id)
