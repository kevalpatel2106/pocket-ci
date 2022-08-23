package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ArtifactIdTest {

    @ParameterizedTest(name = "given string {0} when initialised then check artifact id is valid {1}")
    @MethodSource("provideValuesArtifactIdValidationTest")
    fun `given string when initialised then check artifact id is valid or not`(
        artifactIdSting: String,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { ArtifactId(artifactIdSting) }
        } else {
            assertThrows<AssertionError> { ArtifactId(artifactIdSting) }
        }
    }

    @Test
    fun `given string when converted to artifact id then artifact id created`() {
        val value = "test"

        val actual = value.toArtifactId()

        assertEquals("test", actual.getValue())
    }

    @Test
    fun `given string when converted to artifact id or null then artifact id created`() {
        val value = "test"

        val actual = value.toArtifactIdOrNull()

        assertEquals("test", actual?.getValue())
    }

    @Test
    fun `given null value when converted to artifact id or null then verify null returned`() {
        val value = null

        val actual = value.toArtifactIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesArtifactIdValidationTest() = listOf(
            // Format: string to test, is valid
            arguments("test", true),
            arguments("12345678", true),
            arguments("", false),
            arguments("  ", false),
        )
    }
}
