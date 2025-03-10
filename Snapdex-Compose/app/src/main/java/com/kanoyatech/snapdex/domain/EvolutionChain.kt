package com.kanoyatech.snapdex.domain

data class EvolutionChain(
    val startingPokemon: Pokemon,
    val evolutions: Map<Level, Pokemon>
)