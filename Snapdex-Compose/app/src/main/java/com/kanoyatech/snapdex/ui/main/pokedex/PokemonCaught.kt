package com.kanoyatech.snapdex.ui.main.pokedex

import java.util.Locale

data class PokemonCaught(
    val id: Int,
    val name: Map<Locale, String>
)