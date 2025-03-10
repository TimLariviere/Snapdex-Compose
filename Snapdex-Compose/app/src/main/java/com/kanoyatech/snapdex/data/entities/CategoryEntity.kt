package com.kanoyatech.snapdex.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Categories")
data class CategoryEntity(
    @PrimaryKey val id: Int
)

@Entity("CategoryTranslations")
data class CategoryTranslationEntity(
    @PrimaryKey val categoryTranslationId: Int,
    val categoryId: Int,
    val language: String,
    val name: String
)