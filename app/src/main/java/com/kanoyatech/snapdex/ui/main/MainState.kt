package com.kanoyatech.snapdex.ui.main

import com.kanoyatech.snapdex.domain.models.Pokemon
import com.kanoyatech.snapdex.domain.models.User

data class MainState(
    val user: User? = null,
    val pokemons: List<Pokemon> = emptyList()
)
