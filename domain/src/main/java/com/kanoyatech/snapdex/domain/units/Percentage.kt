package com.kanoyatech.snapdex.domain.units

@JvmInline
value class Percentage(private val value: Double) {
    operator fun minus(other: Percentage): Percentage {
        return Percentage(this.value - other.value)
    }

    fun toFloat(): Float {
        return this.value.toFloat()
    }
}

inline val Double.percent: Percentage get() = Percentage(this / 100.0)