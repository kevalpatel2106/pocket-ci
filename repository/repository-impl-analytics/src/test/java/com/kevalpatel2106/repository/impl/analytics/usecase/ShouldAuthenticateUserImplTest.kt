package com.kevalpatel2106.repository.impl.analytics.usecase

import android.app.Application
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class ShouldAuthenticateUserImplTest {
    private val application = mock<Application>()

    @Test
    fun `given the debug build when invoked then user should not authenticate`() {
        val subject = getSubject(isDebug = true)

        val actual = subject()

        assertFalse(actual)
    }

    @Test
    fun `given the release build with no firebase test lab when invoked then user should authenticate`() {
        val subject = getSubject(isDebug = false)

        val actual = subject()

        assertTrue(actual)
    }

    private fun getSubject(isDebug: Boolean) = ShouldAuthenticateUserImpl(application, isDebug)
}
