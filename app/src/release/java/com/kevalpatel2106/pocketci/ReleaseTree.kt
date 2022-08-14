package com.kevalpatel2106.pocketci

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import timber.log.Timber
import javax.inject.Inject

internal class ReleaseTree @Inject constructor(
    private val displayErrorMapper: DisplayErrorMapper,
) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) addToCrashlyticsLogs(priority, tag, message)
        if (isLoggableAsNonFatal(priority, t)) recordAsNonFatal(requireNotNull(t))
    }

    private fun addToCrashlyticsLogs(priority: Int, tag: String?, message: String) {
        FirebaseCrashlytics.getInstance().log("$priority: $tag -> $message")
    }

    private fun recordAsNonFatal(t: Throwable) {
        FirebaseCrashlytics.getInstance().apply {
            log(displayErrorMapper(t).toString())
            recordException(t)
        }
    }

    override fun isLoggable(tag: String?, priority: Int) = priority >= Log.INFO

    private fun isLoggableAsNonFatal(priority: Int, t: Throwable?) =
        priority >= Log.ERROR && t != null
}
