package com.kanoyatech.snapdex.domain.models

data class EvolutionChain(
    val startingPokemon: Pokemon,
    val evolutions: Map<Level, Pokemon>
)