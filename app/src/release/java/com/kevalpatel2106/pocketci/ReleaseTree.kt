package com.kevalpatel2106.pocketci

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

internal class ReleaseTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            // Log on crashalytics to help debug
            FirebaseCrashlytics.getInstance().log("$priority: $tag -> $message")

            if (priority >= Log.ERROR && t != null) {
                // Record as non fatal
                FirebaseCrashlytics.getInstance().recordException(t)
            }
        }
    }

    override fun isLoggable(tag: String?, priority: Int) = priority >= Log.INFO
}
