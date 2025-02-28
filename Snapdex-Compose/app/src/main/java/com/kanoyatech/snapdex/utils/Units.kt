package com.kanoyatech.snapdex.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@JvmInline
value class Kg(val value: Double)

@Stable
inline val Double.kg: Kg get() = Kg(this)

@Immutable
@JvmInline
value class Meters(val value: Double)

@Stable
inline val Double.meters: Meters get() = Meters(this)

@Immutable
@JvmInline
value class Percentage(val value: Double) {
    operator fun minus(other: Percentage): Percentage {
        return Percentage(this.value - other.value)
    }
}

@Stable
inline val Double.percent: Percentage get() = Percentage(this)

@Stable
fun Percentage.toFloat(): Float {
    return (this.value / 100.0).toFloat()
}