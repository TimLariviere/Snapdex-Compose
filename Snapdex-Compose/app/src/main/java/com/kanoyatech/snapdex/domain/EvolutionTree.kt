package com.kanoyatech.snapdex.domain

data class EvolutionTree(
    val startingPokemon: PokemonId,
    val evolutions: Map<Level, PokemonId>
) {
    companion object {
        val Charmander = EvolutionTree(
            startingPokemon = 4,
            evolutions = mapOf(
                Pair(16, 5),
                Pair(36, 6)
            )
        )
    }
}