package com.kanoyatech.snapdex.domain

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@JvmInline
value class Weight(private val value: Double) {
    fun toKgDouble(): Double { return this.value / 1000.0 }
}

@Stable
inline val Double.kg: Weight get() = Weight(this * 1000.0)