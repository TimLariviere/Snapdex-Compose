package com.kanoyatech.snapdex.ui.main.pokedex

import com.kanoyatech.snapdex.domain.models.PokemonId

sealed interface PokedexEvent {
    data class OnPokemonCatch(val pokemonId: PokemonId): PokedexEvent
}