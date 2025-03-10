package com.kanoyatech.snapdex.domain

import java.util.Locale

interface DataSource {
    suspend fun getAll(locale: Locale): List<Pokemon>
}