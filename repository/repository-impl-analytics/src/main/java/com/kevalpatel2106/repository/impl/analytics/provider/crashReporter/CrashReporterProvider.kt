package com.kevalpatel2106.repository.impl.analytics.provider.crashReporter

internal interface CrashReporterProvider {
    fun log(message: String)
    fun recordException(e: Throwable)
}
