package com.kanoyatech.snapdex.domain

import java.util.Locale

interface DataSource {
    suspend fun getAll(locale: Locale): List<Pokemon>
    suspend fun getBy(id: Int, locale: Locale): Pokemon?
}