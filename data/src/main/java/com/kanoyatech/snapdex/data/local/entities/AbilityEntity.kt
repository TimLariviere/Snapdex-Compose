package com.kanoyatech.snapdex.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("Abilities") data class AbilityEntity(@PrimaryKey val id: Int)

@Entity(
    tableName = "AbilityTranslations",
    foreignKeys =
        [
            ForeignKey(
                entity = AbilityEntity::class,
                parentColumns = ["id"],
                childColumns = ["abilityId"],
            )
        ],
)
data class AbilityTranslationEntity(
    @PrimaryKey val id: Int,
    val abilityId: Int,
    val language: String,
    val name: String,
)
