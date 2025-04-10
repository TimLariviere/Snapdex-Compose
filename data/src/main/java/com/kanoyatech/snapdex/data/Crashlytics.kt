package com.kanoyatech.snapdex.data

import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics

fun FirebaseCrashlytics.recordExceptionWithKeys(throwable: Throwable, keys: Map<String, String>) {
    val builder = CustomKeysAndValues.Builder()
    keys.forEach { (k, v) -> builder.putString(k, v) }
    recordException(throwable, builder.build())
}
