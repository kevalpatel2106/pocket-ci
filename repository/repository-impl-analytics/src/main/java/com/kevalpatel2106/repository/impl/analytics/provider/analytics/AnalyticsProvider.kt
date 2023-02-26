package com.kevalpatel2106.repository.impl.analytics.provider.analytics

internal interface AnalyticsProvider {
    fun log(event: String, params: Map<String, String?>)
    fun setDefaultParameters(params: Map<String, String?>)
}
