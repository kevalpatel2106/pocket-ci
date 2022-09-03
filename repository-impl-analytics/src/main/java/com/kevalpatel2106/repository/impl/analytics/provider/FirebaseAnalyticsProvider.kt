package com.kevalpatel2106.repository.impl.analytics.provider

import com.google.firebase.analytics.FirebaseAnalytics
import com.kevalpatel2106.repository.impl.analytics.toBundle
import javax.inject.Inject

internal class FirebaseAnalyticsProvider @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsProvider {

    override fun log(event: String, params: Map<String, String?>) =
        firebaseAnalytics.logEvent(event, params.toBundle())

    override fun setDefaultParameters(params: Map<String, String?>) =
        firebaseAnalytics.setDefaultEventParameters(params.toBundle())
}
