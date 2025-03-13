package com.kanoyatech.snapdex.domain

import com.kanoyatech.snapdex.domain.units.Length
import com.kanoyatech.snapdex.domain.units.Percentage
import com.kanoyatech.snapdex.domain.units.Weight

typealias UserId = String
typealias PokemonId = Int
typealias Level = Int

data class Pokemon(
    val id: PokemonId,
    val name: String,
    val description: String,
    val types: List<PokemonType>,
    val weaknesses: List<PokemonType>,
    val weight: Weight,
    val height: Length,
    val category: PokemonCategory,
    val ability: PokemonAbility,
    val maleToFemaleRatio: Percentage
)
