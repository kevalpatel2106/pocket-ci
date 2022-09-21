package com.kevalpatel2106.repository.impl.analytics

import com.flextrade.kfixture.KFixture
import com.google.firebase.analytics.FirebaseAnalytics
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.coreTest.buildHttpException
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.analytics.Event
import com.kevalpatel2106.repository.impl.analytics.provider.analytics.AnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.provider.authentication.UserAuthenticationProvider
import com.kevalpatel2106.repository.impl.analytics.provider.crashReporter.CrashReporterProvider
import com.kevalpatel2106.repository.impl.analytics.usecase.ShouldAuthenticateUser
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.net.HttpURLConnection
import kotlin.coroutines.cancellation.CancellationException

internal class AnalyticsRepoImplTest {
    private val fixture = KFixture()
    private val displayError = fixture<DisplayError>()
    private val shouldAuthenticateUser = mock<ShouldAuthenticateUser>()
    private val userAuthenticationProvider = mock<UserAuthenticationProvider>()
    private val crashReporter = mock<CrashReporterProvider>()
    private val analyticsProvider = mock<AnalyticsProvider>()
    private val displayErrorMapper = mock<DisplayErrorMapper> {
        on { invoke(any(), any(), any()) } doReturn displayError
    }

    @Test
    fun `given user should be authenticated when initialise then user authenticated with firebase`() {
        val subject = getAnalyticsRepo()
        whenever(shouldAuthenticateUser()).thenReturn(true)

        subject.initialize()

        verify(userAuthenticationProvider).authenticate()
    }

    @Test
    fun `given user should not be authenticated when initialise then user not authenticated with firebase`() {
        val subject = getAnalyticsRepo()
        whenever(shouldAuthenticateUser()).thenReturn(false)

        subject.initialize()

        verify(userAuthenticationProvider, never()).authenticate()
    }

    @Test
    fun `when screen navigation event send called then screen event logged`() {
        val subject = getAnalyticsRepo()
        val screenName = fixture<String>()

        subject.sendScreenNavigation(screenName)

        val expectedParam = mapOf<String, String?>(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName,
        )
        verify(analyticsProvider).log(
            eq(FirebaseAnalytics.Event.SCREEN_VIEW),
            eq(expectedParam),
        )
    }

    @Test
    fun `when screen navigation event send called then default event parameters set`() {
        val subject = getAnalyticsRepo()
        val screenName = fixture<String>()

        subject.sendScreenNavigation(screenName)

        val expectedParam = mapOf<String, String?>(
            FirebaseAnalytics.Param.SCREEN_NAME to screenName,
            FirebaseAnalytics.Param.SCREEN_CLASS to screenName,
        )
        verify(analyticsProvider).setDefaultParameters(eq(expectedParam))
    }

    @Test
    fun `when send event called then event logged in analytics provider`() {
        val subject = getAnalyticsRepo()
        val event = object : Event(fixture()) {
            override val properties: Map<String, String?> = mapOf(fixture<String>() to fixture())
        }

        subject.sendEvent(event)

        verify(analyticsProvider).log(eq(event.name.value), eq(event.properties))
    }

    @Test
    fun `when send log with tag called then log sent to crashlytics`() {
        val subject = getAnalyticsRepo()
        val tag = fixture<String>()
        val message = fixture<String>()

        subject.sendLog(tag, message)

        verify(crashReporter).log("$tag-> $message")
    }

    @Test
    fun `when send log with no tag called then log sent to crashlytics`() {
        val subject = getAnalyticsRepo()
        val message = fixture<String>()

        subject.sendLog(null, message)

        verify(crashReporter).log("-> $message")
    }

    @Test
    fun `when send exception called then original exception recorded to crashalytics`() {
        val subject = getAnalyticsRepo()
        val originalException = Throwable()

        subject.sendNonFatalException(originalException)

        verify(crashReporter).recordException(originalException)
    }

    @Test
    fun `given unauthorised exception when send exception called then exception not recorded to crashalytics`() {
        val subject = getAnalyticsRepo()
        val exception = buildHttpException(HttpURLConnection.HTTP_UNAUTHORIZED, fixture)

        subject.sendNonFatalException(exception)

        verify(crashReporter, never()).recordException(exception)
    }

    @Test
    fun `given cancellation exception when send exception called then exception not recorded to crashalytics`() {
        val subject = getAnalyticsRepo()
        val exception = CancellationException()

        subject.sendNonFatalException(exception)

        verify(crashReporter, never()).recordException(exception)
    }

    @Test
    fun `given display error mapping success when send exception called then display error logged to crashalytics`() {
        val subject = getAnalyticsRepo()

        subject.sendNonFatalException(Throwable())

        verify(crashReporter).log(displayError.toString())
    }

    @Test
    fun `given display error mapping fails when send exception called then exception logged to crashalytics`() {
        val subject = getAnalyticsRepo()
        whenever(displayErrorMapper.invoke(any(), any(), any())).thenThrow(IllegalStateException())
        val originalException = Throwable(message = "Message")

        subject.sendNonFatalException(originalException)

        verify(crashReporter).log(originalException.toString())
    }

    private fun getAnalyticsRepo(isDebug: Boolean = fixture()) = AnalyticsRepoImpl(
        isDebug,
        shouldAuthenticateUser,
        userAuthenticationProvider,
        crashReporter,
        analyticsProvider,
        displayErrorMapper,
    )
}
