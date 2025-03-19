package com.kanoyatech.snapdex.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("Categories")
data class CategoryEntity(
    @PrimaryKey val id: Int
)

@Entity(
    tableName = "CategoryTranslations",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class CategoryTranslationEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val language: String,
    val name: String
)