package com.kevalpatel2106.repository.impl.analytics.provider.crashReporter

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

internal class FirebaseCrashReporterProvider @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : CrashReporterProvider {
    override fun log(message: String) = firebaseCrashlytics.log(message)
    override fun recordException(e: Throwable) = firebaseCrashlytics.recordException(e)
}
