package com.kanoyatech.snapdex.ui.mappers

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Abilities
import com.kanoyatech.snapdex.domain.Category
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.ui.utils.PokemonResourceProvider

val Pokemon.name: String @Composable get() =
    stringResource(id =
        PokemonResourceProvider.getPokemonNameResourceId(
            LocalContext.current,
            this.id
        )
    )

val Pokemon.description: String @Composable get() =
    stringResource(id =
        PokemonResourceProvider.getPokemonDescriptionResourceId(
            LocalContext.current,
            this.id
        )
    )

val Pokemon.largeImageId: Int @Composable @DrawableRes get() =
    PokemonResourceProvider.getPokemonLargeImageId(
        LocalContext.current,
        this.id
    )

val Pokemon.mediumImageId: Int @Composable @DrawableRes get() =
    PokemonResourceProvider.getPokemonMediumImageId(
        LocalContext.current,
        this.id
    )

val Pokemon.smallImageId: Int @Composable @DrawableRes get() =
    PokemonResourceProvider.getPokemonSmallImageId(
        LocalContext.current,
        this.id
    )

val Pokemon.categoryStr: String @Composable get() =
    stringResource(id =
        when(category) {
            Category.LIZARD -> R.string.category_lizard
            Category.FLAME -> R.string.category_flame
            Category.SEED -> R.string.category_seed
        }
    )

val Pokemon.abilitiesStr: String @Composable get() =
    stringResource(id =
        when(abilities) {
            Abilities.BLAZE -> R.string.abilities_blaze
            Abilities.OVERGROW -> R.string.abilities_overgrow
        }
    )