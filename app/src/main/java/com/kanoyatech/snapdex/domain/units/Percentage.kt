package com.kanoyatech.snapdex.domain.units

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@JvmInline
value class Percentage(private val value: Double) {
    operator fun minus(other: Percentage): Percentage {
        return Percentage(this.value - other.value)
    }

    fun toDouble(): Double {
        return this.value
    }

    fun toFloat(): Float {
        return this.value.toFloat()
    }
}

@Stable
inline val Double.percent: Percentage get() = Percentage(this / 100.0)