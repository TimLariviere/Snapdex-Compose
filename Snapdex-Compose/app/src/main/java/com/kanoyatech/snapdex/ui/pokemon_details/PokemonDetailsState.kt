package com.kanoyatech.snapdex.ui.pokemon_details

import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Pokemon

data class PokemonDetailsState(
    val pokemon: Pokemon,
    val evolution: Evolution,
    var isFavorite: Boolean = false
)