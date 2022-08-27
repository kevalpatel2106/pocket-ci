package com.kevalpatel2106.pocketci.initializer

import android.util.Log
import com.kevalpatel2106.repository.AnalyticsRepo
import timber.log.Timber
import javax.inject.Inject

internal class TimberAnalyticsTree @Inject constructor(
    private val analyticsRepo: AnalyticsRepo,
) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) analyticsRepo.sendLog(priority, tag, message)
        if (t != null && isLoggableAsNonFatal(priority, t)) analyticsRepo.sendNonFatalException(t)
    }

    override fun isLoggable(tag: String?, priority: Int) = priority >= Log.INFO

    private fun isLoggableAsNonFatal(priority: Int, t: Throwable?) =
        priority >= Log.ERROR && t != null
}
