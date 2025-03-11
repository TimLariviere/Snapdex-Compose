package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.PokemonId

sealed interface PokedexAction {
    data class OnPokemonClick(val pokemonId: PokemonId): PokedexAction
}