package com.kanoyatech.snapdex.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Abilities")
data class AbilityEntity(
    @PrimaryKey val id: Int
)

@Entity("AbilityTranslations")
data class AbilityTranslationEntity(
    @PrimaryKey val abilityTranslationId: Int,
    val abilityId: Int,
    val language: String,
    val name: String
)