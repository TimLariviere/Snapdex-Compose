package com.kanoyatech.snapdex.domain.units

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@JvmInline
value class Length(private val value: Double) {
    fun toMeters(): Double {
        return this.value / 1000.0
    }
}

@Stable
inline val Double.m: Length get() = Length(this * 1000.0)