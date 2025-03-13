package com.kanoyatech.snapdex.ui.main.pokedex

import android.graphics.Bitmap
import com.kanoyatech.snapdex.domain.PokemonId

sealed interface PokedexAction {
    data class OnPokemonClick(val pokemonId: PokemonId): PokedexAction
    data class IsCameraGrantedChange(val isGranted: Boolean) : PokedexAction
    data class OnPhotoTake(val bitmap: Bitmap) : PokedexAction
}