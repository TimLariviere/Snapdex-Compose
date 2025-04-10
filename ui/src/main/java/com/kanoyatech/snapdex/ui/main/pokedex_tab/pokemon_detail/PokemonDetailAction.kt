package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokemon_detail

import com.kanoyatech.snapdex.domain.models.PokemonId

sealed interface PokemonDetailAction {
    object OnBackClick : PokemonDetailAction

    data class OnPokemonClick(val pokemonId: PokemonId) : PokemonDetailAction
}
