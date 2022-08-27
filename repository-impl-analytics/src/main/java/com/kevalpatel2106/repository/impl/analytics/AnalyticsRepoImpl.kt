package com.kevalpatel2106.repository.impl.analytics

import android.util.Log
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.entity.analytics.Event
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.di.IsDebug
import com.kevalpatel2106.repository.impl.analytics.usecase.FirebaseAuthenticateUser
import javax.inject.Inject

internal class AnalyticsRepoImpl @Inject constructor(
    @IsDebug private val isDebug: Boolean,
    private val firebaseAuthenticateUser: FirebaseAuthenticateUser,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: FirebaseAnalytics,
    private val displayErrorMapper: DisplayErrorMapper,
) : AnalyticsRepo {

    override fun initialize() {
        firebaseAuthenticateUser()
    }

    override fun sendScreenNavigation(screenName: String?) {
        if (isDebug) return

        val params = bundleOf(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName,
        )

        if (screenName != null) {
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        }
        firebaseAnalytics.setDefaultEventParameters(params)
    }

    override fun sendEvent(event: Event) {
        if (isDebug) {
            Log.d(ANALYTICS_TAG, "Event: $event")
        } else {
            firebaseAnalytics.logEvent(event.name.value, event.properties.toBundle())
        }
    }

    override fun sendLog(priority: Int, tag: String?, message: String) {
        val text = "${tag.orEmpty()}-> $message"
        if (isDebug) {
            Log.d(ANALYTICS_TAG, "Log: $text")
        } else {
            firebaseCrashlytics.log(text)
        }
    }

    override fun sendNonFatalException(e: Throwable) {
        firebaseCrashlytics.recordException(e)

        var displayError = ""
        runCatching { displayErrorMapper(e).toString() }
            .onSuccess { displayError = it }
            .onFailure { displayError = e.toString() }
        if (isDebug) {
            Log.d(ANALYTICS_TAG, "NonFatalException: $displayError")
        } else {
            firebaseCrashlytics.log(displayError)
        }
    }

    companion object {
        private const val ANALYTICS_TAG = "~~ANALYTICS~~"
    }
}
