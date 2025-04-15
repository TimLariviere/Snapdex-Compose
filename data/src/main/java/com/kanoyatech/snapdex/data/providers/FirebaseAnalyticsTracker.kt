package com.kanoyatech.snapdex.data.providers

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.kanoyatech.snapdex.domain.models.UserId
import com.kanoyatech.snapdex.domain.providers.AnalyticsTracker

class FirebaseAnalyticsTracker(private val analytics: FirebaseAnalytics) : AnalyticsTracker {
    override fun setUserId(userId: UserId?) {
        analytics.setUserId(userId)
    }

    override fun logEvent(name: String, parameters: Map<String, String>?) {
        val bundle =
            Bundle().apply { parameters?.forEach { (key, value) -> putString(key, value) } }

        analytics.logEvent(name, bundle)
    }
}
