package com.kanoyatech.snapdex.domain.models

import com.kanoyatech.snapdex.domain.units.Length
import com.kanoyatech.snapdex.domain.units.Percentage
import com.kanoyatech.snapdex.domain.units.Weight
import java.util.Locale

data class Pokemon(
    val id: PokemonId,
    val name: Map<Locale, String>,
    val description: Map<Locale, String>,
    val types: List<PokemonType>,
    val weaknesses: List<PokemonType>,
    val weight: Weight,
    val height: Length,
    val category: PokemonCategory,
    val ability: PokemonAbility,
    val maleToFemaleRatio: Percentage,
)
