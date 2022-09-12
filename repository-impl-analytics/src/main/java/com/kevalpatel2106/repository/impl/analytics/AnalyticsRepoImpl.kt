package com.kevalpatel2106.repository.impl.analytics

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.isUnAuthorized
import com.kevalpatel2106.entity.analytics.Event
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.di.IsDebug
import com.kevalpatel2106.repository.impl.analytics.provider.AnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.usecase.FirebaseAuthenticateUser
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

internal class AnalyticsRepoImpl @Inject constructor(
    @IsDebug private val isDebug: Boolean,
    private val firebaseAuthenticateUser: FirebaseAuthenticateUser,
    private val firebaseCrashlytics: FirebaseCrashlytics,
    private val firebaseAnalytics: AnalyticsProvider,
    private val displayErrorMapper: DisplayErrorMapper,
) : AnalyticsRepo {

    override fun initialize() {
        firebaseAuthenticateUser()
    }

    override fun sendScreenNavigation(screenName: String?) {
        val params = mapOf(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName,
        )

        if (screenName != null) {
            firebaseAnalytics.log(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        }
        firebaseAnalytics.setDefaultParameters(params)
    }

    override fun sendEvent(event: Event) {
        if (isDebug) Log.d(ANALYTICS_TAG, "Event: $event")
        firebaseAnalytics.log(event.name.value, event.properties)
    }

    override fun sendLog(tag: String?, message: String) {
        val text = "${tag.orEmpty()}-> $message"
        if (isDebug) Log.d(ANALYTICS_TAG, "Log: $text")
        firebaseCrashlytics.log(text)
    }

    override fun sendNonFatalException(e: Throwable) {
        if (e.isUnAuthorized() || e is CancellationException) return

        var displayError = ""
        runCatching { displayErrorMapper(e).toString() }
            .onSuccess { displayError = it }
            .onFailure { displayError = e.toString() }
        if (isDebug) Log.d(ANALYTICS_TAG, "NonFatalException: $displayError")
        firebaseCrashlytics.recordException(e)
        firebaseCrashlytics.log(displayError)
    }

    companion object {
        private const val ANALYTICS_TAG = "~~ANALYTICS~~"
    }
}
