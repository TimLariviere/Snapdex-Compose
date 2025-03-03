package com.kanoyatech.snapdex.domain

data class Evolution(
    val startingPokemon: PokemonId,
    val evolutions: Map<Level, PokemonId>
) {
    companion object {
        val all = listOf(
            Evolution(
                startingPokemon = 4,
                evolutions = mapOf(
                    Pair(16, 5),
                    Pair(36, 6)
                )
            )
        )

        fun find(id: PokemonId): Evolution {
            val tree = all.find { evolution ->
                evolution.startingPokemon == id || evolution.evolutions.containsValue(id)
            }
            return tree!!
        }
    }
}