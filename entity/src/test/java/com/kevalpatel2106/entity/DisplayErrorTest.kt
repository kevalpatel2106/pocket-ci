package com.kevalpatel2106.entity

import com.flextrade.kfixture.KFixture
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * Goal of this test is not to check the exact string but rather to check if all the nessesory info
 * printed in the string (in whatever format) or not.
 */
internal class DisplayErrorTest {

    @ParameterizedTest(name = "given display error when convert to string then check string contains {1} = {2}")
    @MethodSource("provideValues")
    fun `given display error when convert to string then check string contains time`(
        displayError: DisplayError,
        expectedProperty: String,
        expectedValue: String,
    ) {
        assertTrue(displayError.toString().contains(expectedValue)) {
            "Property $expectedProperty with value $expectedValue not found in string $displayError"
        }
    }

    companion object {
        private val fixture = KFixture()
        private val displayError = fixture<DisplayError>()

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: displayError, expected property, expected value
            arguments(displayError, "time", displayError.time.toString()),
            arguments(displayError, "headline", displayError.headline),
            arguments(displayError, "message", displayError.message),
            arguments(displayError, "technicalMessage", displayError.technicalMessage),
            arguments(displayError, "throwable", displayError.throwable.stackTraceToString()),
            arguments(displayError, "url", displayError.url),
            arguments(displayError, "httpResponseCode", displayError.httpResponseCode.toString()),
            arguments(displayError, "httpResponse", displayError.httpResponse),
            arguments(displayError, "unableToTriage", displayError.unableToTriage.not().toString()),
            arguments(displayError, "nonRecoverable", displayError.nonRecoverable.not().toString()),
        )
    }
}
