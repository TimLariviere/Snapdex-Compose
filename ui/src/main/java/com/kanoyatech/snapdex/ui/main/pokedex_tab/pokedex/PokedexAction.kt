package com.kanoyatech.snapdex.ui.main.pokedex_tab.pokedex

import android.graphics.Bitmap
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.models.PokemonType

sealed interface PokedexAction {
    data class OnPokemonClick(val pokemonId: PokemonId): PokedexAction
    data class IsCameraGrantedChange(val isGranted: Boolean) : PokedexAction
    data class OnPhotoTake(val bitmap: Bitmap) : PokedexAction
    data class RemoveFilterClick(val type: PokemonType) : PokedexAction
    object OnRecognitionOverlayDismiss : PokedexAction
}