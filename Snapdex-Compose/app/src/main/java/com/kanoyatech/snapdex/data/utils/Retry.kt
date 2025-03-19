package com.kanoyatech.snapdex.data.utils

import kotlinx.coroutines.delay

object Retry {
    suspend fun <T> execute(
        body: suspend () -> T,
        retryIf: (Exception) -> Boolean = { true },
        maxAttempts: Int = 3
    ): Result<T> {
        var attempt = 0
        var lastException: Exception? = null

        while (attempt < maxAttempts) {
            try {
                val result = body()
                return Result.success(result)
            } catch (e: Exception) {
                lastException = e

                if (retryIf(e)) {
                    delay(1000L * (attempt + 1))
                } else {
                    break
                }
            }

            attempt++
        }

        return Result.failure(lastException!!)
    }
}