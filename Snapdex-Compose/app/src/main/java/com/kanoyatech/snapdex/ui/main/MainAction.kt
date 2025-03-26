package com.kanoyatech.snapdex.ui.main

import com.kanoyatech.snapdex.domain.models.PokemonId

sealed interface MainAction {
    data class OnPokemonCatch(val pokemonId: PokemonId): MainAction
    object OnLoggedOut : MainAction
}
