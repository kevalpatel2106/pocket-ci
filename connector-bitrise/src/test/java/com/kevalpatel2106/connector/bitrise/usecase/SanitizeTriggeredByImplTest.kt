package com.kevalpatel2106.connector.bitrise.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SanitizeTriggeredByImplTest {

    private val subject = SanitizeTriggeredByImpl()

    @ParameterizedTest(name = "given build status {0} when invoked then build image is {1}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check image`(
        inputTriggeredBy: String,
        expectedTriggeredBy: String?,
    ) {
        assertEquals(expectedTriggeredBy, subject(inputTriggeredBy))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: input triggered by, sanitized triggered by
            arguments("trigger", "trigger"),
            arguments("/", null),
            arguments("github/kevalpatel2106", "kevalpatel2106"),
            arguments("github/kevalpatel2106/pocket-ci", "pocket-ci"),
            arguments("github//", null),
        )
    }
}
