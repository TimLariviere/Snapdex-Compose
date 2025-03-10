package com.kanoyatech.snapdex.ui

import androidx.compose.runtime.Composable
import com.kanoyatech.snapdex.domain.Evolution
import com.kanoyatech.snapdex.domain.Level
import com.kanoyatech.snapdex.domain.Pokemon

//data class EvolutionUi(
//    val startingPokemon: Pokemon,
//    val evolutions: Map<Level, Pokemon>
//) {
//    companion object {
//        @Composable
//        fun fromEvolution(evolution: Evolution): EvolutionUi {
//            return EvolutionUi(
//                startingPokemon = Pokemon.find(evolution.startingPokemon),
//                evolutions = evolution.evolutions.mapValues { (_, pokemonId) ->
//                    Pokemon.find(pokemonId)
//                }
//            )
//        }
//    }
//}