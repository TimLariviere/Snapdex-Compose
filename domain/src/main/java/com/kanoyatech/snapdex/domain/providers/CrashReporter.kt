package com.kanoyatech.snapdex.domain.providers

interface CrashReporter {
    fun recordException(exception: Throwable, metadata: Map<String, String>? = null)
}
