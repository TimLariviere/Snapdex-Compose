package com.kanoyatech.snapdex.data.providers

import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kanoyatech.snapdex.domain.providers.CrashReporter

class FirebaseCrashReporter(private val crashlytics: FirebaseCrashlytics) : CrashReporter {
    override fun recordException(exception: Throwable, metadata: Map<String, String>?) {
        val builder =
            CustomKeysAndValues.Builder()
                .apply { metadata?.forEach { (key, value) -> putString(key, value) } }
                .build()

        crashlytics.recordException(exception, builder)
    }
}
