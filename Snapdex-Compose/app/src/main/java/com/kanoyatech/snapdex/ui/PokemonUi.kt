package com.kanoyatech.snapdex.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kanoyatech.snapdex.R
import com.kanoyatech.snapdex.domain.Abilities
import com.kanoyatech.snapdex.domain.Category
import com.kanoyatech.snapdex.domain.Length
import com.kanoyatech.snapdex.domain.Percentage
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.domain.Weight
import com.kanoyatech.snapdex.ui.utils.PokemonResourceProvider

data class PokemonUi(
    val id: PokemonId,
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val bigImage: Int,
    @DrawableRes val smallImage: Int,
    val type: List<TypeUi>,
    val weakness: List<TypeUi>,
    val weight: Weight,
    val height: Length,
    @StringRes val category: Int,
    @StringRes val abilities: Int,
    val maleToFemaleRatio: Percentage
) {
    companion object {
        @Composable
        fun fromPokemon(pokemon: Pokemon): PokemonUi {
            val context = LocalContext.current
            return PokemonUi(
                id = pokemon.id,
                name = PokemonResourceProvider.getPokemonNameResourceId(context, pokemon.id),
                description = PokemonResourceProvider.getPokemonDescriptionResourceId(context, pokemon.id),
                bigImage = PokemonResourceProvider.getPokemonBigImageId(context, pokemon.id),
                smallImage = PokemonResourceProvider.getPokemonSmallImageId(context, pokemon.id),
                type = pokemon.type.map { TypeUi.fromType(it) },
                weakness = pokemon.weakness.map { TypeUi.fromType(it) },
                weight = pokemon.weight,
                height = pokemon.height,
                category = convertCategory(pokemon.category),
                abilities = convertAbilities(pokemon.abilities),
                maleToFemaleRatio = pokemon.maleToFemaleRatio
            )
        }

        private fun convertCategory(category: Category): Int {
            val res = when(category) {
                Category.LIZARD -> R.string.category_lizard
                Category.FLAME -> R.string.category_flame
            }

            return res
        }

        private fun convertAbilities(abilities: Abilities): Int {
            val res = when(abilities) {
                Abilities.BLAZE -> R.string.abilities_blaze
            }

            return res
        }
    }
}