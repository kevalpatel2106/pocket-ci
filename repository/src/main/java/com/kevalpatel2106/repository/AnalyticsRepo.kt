package com.kevalpatel2106.repository

import com.kevalpatel2106.entity.analytics.Event

interface AnalyticsRepo {
    fun initialize()
    fun sendScreenNavigation(screenName: String?)
    fun sendEvent(event: Event)
    fun sendLog(priority: Int, tag: String?, message: String)
    fun sendNonFatalException(e: Throwable)
}
