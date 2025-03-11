package com.kanoyatech.snapdex.ui.main.pokemon_details

import com.kanoyatech.snapdex.domain.EvolutionChain
import com.kanoyatech.snapdex.domain.Pokemon

data class PokemonDetailsState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    var isFavorite: Boolean = false
)