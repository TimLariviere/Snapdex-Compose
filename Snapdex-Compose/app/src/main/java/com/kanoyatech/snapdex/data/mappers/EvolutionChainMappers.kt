package com.kanoyatech.snapdex.data.mappers

import com.kanoyatech.snapdex.data.dao.EvolutionChainWithRelations
import com.kanoyatech.snapdex.domain.EvolutionChain
import com.kanoyatech.snapdex.domain.Level
import com.kanoyatech.snapdex.domain.Pokemon
import java.util.Locale

fun EvolutionChainWithRelations.toEvolutionChain(locale: Locale): EvolutionChain {
    val evolutions = mutableMapOf<Level, Pokemon>()

    this.evolvesTo.forEach { link ->
        evolutions[link.minLevel] = link.pokemon.toPokemon(locale)
    }

    return EvolutionChain(
        startingPokemon = this.startingPokemon.toPokemon(locale),
        evolutions = evolutions
    )
}