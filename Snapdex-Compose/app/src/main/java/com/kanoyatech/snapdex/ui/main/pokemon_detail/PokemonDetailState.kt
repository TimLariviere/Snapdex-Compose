package com.kanoyatech.snapdex.ui.main.pokemon_detail

import com.kanoyatech.snapdex.domain.EvolutionChain
import com.kanoyatech.snapdex.domain.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    var isFavorite: Boolean = false
)