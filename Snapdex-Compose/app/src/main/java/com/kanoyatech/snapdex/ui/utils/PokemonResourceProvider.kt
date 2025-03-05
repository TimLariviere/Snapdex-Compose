package com.kanoyatech.snapdex.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import com.kanoyatech.snapdex.domain.PokemonId

object PokemonResourceProvider {
    fun getPokemonNameResourceId(context: Context, id: PokemonId): Int {
        return getResourceId(context, "string", id, "name")
    }
    fun getPokemonDescriptionResourceId(context: Context, id: PokemonId): Int {
        return getResourceId(context, "string", id, "description")
    }

    fun getPokemonLargeImageId(context: Context, id: PokemonId): Int {
        return getResourceId(context, "drawable", id, "large")
    }

    fun getPokemonMediumImageId(context: Context, id: PokemonId): Int {
        return getResourceId(context, "drawable", id, "medium")
    }

    fun getPokemonSmallImageId(context: Context, id: PokemonId): Int {
        return getResourceId(context, "drawable", id, "small")
    }

    // We are dynamically generating resource names, so we have no other choices
    @SuppressLint("DiscouragedApi")
    private fun getResourceId(
        context: Context,
        resourceType: String,
        id: PokemonId,
        suffix: String
    ): Int {
        val paddedId = id.toString().padStart(4, '0')
        val resourceName = "pokemon_${paddedId}_$suffix"
        return context.resources.getIdentifier(resourceName, resourceType, context.packageName)
    }
}