package com.kanoyatech.snapdex.usecases

import com.kanoyatech.snapdex.domain.TypedResult
import kotlinx.coroutines.delay

object Retry {
    @Suppress("UNCHECKED_CAST")
    suspend fun <T, E> execute(
        body: suspend () -> TypedResult<T, E>,
        mapFailure: (Throwable) -> E,
        retryIf: (E) -> Boolean = { true },
        maxAttempts: Int = 3,
    ): TypedResult<T, E> {
        var attempt = 0
        var lastError: E? = null

        while (attempt < maxAttempts) {
            try {
                val result = body()
                when (result) {
                    is TypedResult.Error -> {
                        lastError = result.error

                        if (retryIf(result.error)) {
                            delay(1000L * (attempt + 1))
                        } else {
                            break
                        }

                        attempt++
                    }
                    else -> return result
                }
            } catch (e: Exception) {
                return TypedResult.Error(mapFailure(e))
            }

            attempt++
        }

        return TypedResult.Error(lastError!!)
    }
}
