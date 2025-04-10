package com.kanoyatech.snapdex.domain

import com.kanoyatech.snapdex.domain.models.PokemonId
import java.nio.ByteBuffer

interface Classifier {
    suspend fun init()

    suspend fun classify(bitmap: ByteBuffer): PokemonId?
}
