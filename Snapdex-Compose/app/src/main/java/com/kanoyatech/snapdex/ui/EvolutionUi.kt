package com.kanoyatech.snapdex.ui

import androidx.compose.runtime.Composable
import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Level
import com.kanoyatech.snapdex.domain.Pokemon

data class EvolutionUi(
    val startingPokemon: PokemonUi,
    val evolutions: Map<Level, PokemonUi>
) {
    companion object {
        @Composable
        fun fromEvolution(evolution: Evolution): EvolutionUi {
            return EvolutionUi(
                startingPokemon = PokemonUi.fromPokemon(Pokemon.find(evolution.startingPokemon)),
                evolutions = evolution.evolutions.mapValues { (_, pokemonId) ->
                    val pokemon = Pokemon.find(pokemonId)
                    PokemonUi.fromPokemon(pokemon)
                }
            )
        }
    }
}