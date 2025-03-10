package com.kanoyatech.snapdex.ui.pokemon_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Pokemon
import com.kanoyatech.snapdex.domain.PokemonAbility
import com.kanoyatech.snapdex.domain.PokemonCategory
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.domain.PokemonType
import com.kanoyatech.snapdex.domain.units.kg
import com.kanoyatech.snapdex.domain.units.m
import com.kanoyatech.snapdex.domain.units.percent

class PokemonDetailsViewModel(
    pokemonId: PokemonId
): ViewModel() {
    var state by mutableStateOf(PokemonDetailsState(
        pokemon = Pokemon(
            id = 6,
            name = "Charizard",
            description = "If Charizard becomes truly angered, the flame at the tip of its tail burns in a light blue shade.",
            types = listOf(
                PokemonType.FIRE,
                PokemonType.FLYING
            ),
            weaknesses = emptyList(),
            weight = 120.0.kg,
            height = 1.70.m,
            category = PokemonCategory(id = 0, name = "Lizard"),
            ability = PokemonAbility(id = 0, name = "Blaze"),
            maleToFemaleRatio = 87.5.percent
        ),
        evolution = Evolution.find(pokemonId)
    ))
        private set

    fun onAction(action: PokemonDetailsAction) {
        when (action) {
            PokemonDetailsAction.OnFavoriteToggleClick ->
                state = state.copy(isFavorite = !state.isFavorite)
            else -> Unit
        }
    }
}