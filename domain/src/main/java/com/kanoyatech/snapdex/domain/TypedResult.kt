package com.kanoyatech.snapdex.domain

sealed interface TypedResult<out D, out E> {
    data class Success<out D>(val data: D) : TypedResult<D, Nothing>

    data class Error<out E>(val error: E) : TypedResult<Nothing, E>
}

inline fun <D, E> TypedResult<D, E>.onError(fn: (E) -> Unit): D? {
    return when (this) {
        is TypedResult.Success -> {
            this.data
        }
        is TypedResult.Error -> {
            fn(this.error)
            return null
        }
    }
}
