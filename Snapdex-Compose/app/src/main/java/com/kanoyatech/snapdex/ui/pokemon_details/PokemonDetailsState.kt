package com.kanoyatech.snapdex.ui.pokemon_details

import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Pokemon

data class PokemonDetailsState(
    val pokemonId: Int,
    val pokemon: Pokemon? = null,
    val evolution: Evolution? = null,
    var isFavorite: Boolean = false
)