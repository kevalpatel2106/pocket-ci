package com.kevalpatel2106.repository.impl.analytics.provider.crashReporter

import com.flextrade.kfixture.KFixture
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class FirebaseCrashReporterProviderTest {
    private val fixture = KFixture()
    private val firebaseCrashlytics = mock<FirebaseCrashlytics>()
    private val subject = FirebaseCrashReporterProvider(firebaseCrashlytics)

    @Test
    fun `given exception when exception recorded then verify crashlytics called`() {
        val exception = IllegalStateException()

        subject.recordException(exception)

        verify(firebaseCrashlytics).recordException(exception)
    }

    @Test
    fun `given message when message logged then verify crashlytics called`() {
        val message = fixture<String>()

        subject.log(message)

        verify(firebaseCrashlytics).log(message)
    }
}
