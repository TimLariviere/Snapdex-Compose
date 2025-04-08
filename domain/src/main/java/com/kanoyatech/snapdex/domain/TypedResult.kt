package com.kanoyatech.snapdex.domain

sealed interface TypedResult<out D, out E> {
    data class Success<out D>(val data: D): TypedResult<D, Nothing>
    data class Error<out E>(val error: E): TypedResult<Nothing, E>
}