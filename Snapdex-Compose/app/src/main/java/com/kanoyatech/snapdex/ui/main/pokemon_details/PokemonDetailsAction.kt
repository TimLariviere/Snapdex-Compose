package com.kanoyatech.snapdex.ui.main.pokemon_details

import com.kanoyatech.snapdex.domain.PokemonId

sealed interface PokemonDetailsAction {
    data object OnFavoriteToggleClick: PokemonDetailsAction
    data object OnBackClick: PokemonDetailsAction
    data class OnPokemonClick(val pokemonId: PokemonId): PokemonDetailsAction
}