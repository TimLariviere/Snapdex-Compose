package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail

import com.kanoyatech.snapdex.domain.models.EvolutionChain
import com.kanoyatech.snapdex.domain.models.Pokemon

data class PokemonDetailState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
)
