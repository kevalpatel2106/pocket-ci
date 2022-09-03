package com.kevalpatel2106.pocketci.initializer

import android.util.Log
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.repository.AnalyticsRepo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

internal class TimberAnalyticsTreeTest {
    private val fixture = KFixture()
    private val analyticsRepo = mock<AnalyticsRepo>()
    private val subject = TimberAnalyticsTree(analyticsRepo)

    @ParameterizedTest(name = "given priority {0} check is logged to analytics {1}")
    @MethodSource("provideValuesForLogs")
    fun `given priority check is logged to analytics`(priority: Int, shouldLog: Boolean) {
        subject.log(priority, Throwable(), fixture<String>(), fixture<String>())

        if (shouldLog) {
            verify(analyticsRepo).sendLog(anyOrNull(), anyOrNull())
        } else {
            verify(analyticsRepo, never()).sendLog(anyOrNull(), anyOrNull())
        }
    }

    @ParameterizedTest(name = "given priority {0} and exception {1} check exception recorded {1}")
    @MethodSource("provideValuesForException")
    fun `given priority and exception check exception recorded or not`(
        priority: Int,
        throwable: Throwable?,
        shouldRecord: Boolean,
    ) {
        subject.log(priority, throwable, fixture(), fixture<String>())

        if (shouldRecord) {
            verify(analyticsRepo).sendNonFatalException(throwable!!)
        } else {
            verify(analyticsRepo, never()).sendNonFatalException(anyOrNull())
        }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValuesForLogs() = listOf(
            arguments(Log.VERBOSE - 1, false),
            arguments(Log.VERBOSE, false),
            arguments(Log.DEBUG, false),
            arguments(Log.INFO, true),
            arguments(Log.WARN, true),
            arguments(Log.ERROR, true),
            arguments(Log.ASSERT, true),
            arguments(Log.ASSERT + 1, true),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForException() = listOf(
            arguments(Log.VERBOSE - 1, IllegalStateException(), false),
            arguments(Log.VERBOSE, IllegalStateException(), false),
            arguments(Log.DEBUG, IllegalStateException(), false),
            arguments(Log.INFO, IllegalStateException(), false),
            arguments(Log.WARN, IllegalStateException(), false),
            arguments(Log.ERROR, IllegalStateException(), true),
            arguments(Log.ASSERT, IllegalStateException(), true),
            arguments(Log.ASSERT + 1, IllegalStateException(), true),
            arguments(Log.VERBOSE - 1, null, false),
            arguments(Log.VERBOSE, null, false),
            arguments(Log.DEBUG, null, false),
            arguments(Log.INFO, null, false),
            arguments(Log.WARN, null, false),
            arguments(Log.ERROR, null, false),
            arguments(Log.ASSERT, null, false),
            arguments(Log.ASSERT + 1, null, false),
        )
    }
}
