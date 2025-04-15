package com.kanoyatech.snapdex.domain.providers

import com.kanoyatech.snapdex.domain.models.UserId

interface AnalyticsTracker {
    fun setUserId(userId: UserId?)

    fun logEvent(name: String, parameters: Map<String, String>? = null)
}
