package com.kanoyatech.snapdex.domain.units

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@JvmInline
value class Weight(private val value: Double) {
    fun toKgDouble(): Double { return this.value / 1000.0 }

    companion object {
        fun fromHectogram(value: Double): Weight {
            return (value / 10.0).kg
        }
    }
}

@Stable
inline val Double.kg: Weight get() = Weight(this * 1000.0)