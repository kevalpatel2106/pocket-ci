package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class StepIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check step id is valid {1}")
    @MethodSource("provideValuesStepIdValidationTest")
    fun `given string when initialised then check step id is valid or not`(
        stepIdString: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { StepId(stepIdString) }
        } else {
            assertThrows<AssertionError> { StepId(stepIdString) }
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesStepIdValidationTest() = listOf(
            // Format: string to test, is valid
            Arguments.arguments("test", true),
            Arguments.arguments("12345678", true),
            Arguments.arguments("", false),
            Arguments.arguments("  ", false),
        )
    }
}
