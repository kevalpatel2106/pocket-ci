package com.kevalpatel2106.entity.id

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AccountIdTest {

    @ParameterizedTest(name = "given long {0} when initialised then check account id is valid {1}")
    @MethodSource("provideValuesAccountIdValidationTest")
    fun `given long when initialised then check account id is valid or not`(
        accountLong: Long,
        isValid: Boolean,
    ) {
        if (isValid) {
            assertDoesNotThrow { AccountId(accountLong) }
        } else {
            assertThrows<AssertionError> { AccountId(accountLong) }
        }
    }

    @Test
    fun `given long when converted to account id then account id created`() {
        val value = 1L

        val actual = value.toAccountId()

        assertEquals(1L, actual.getValue())
    }

    @Test
    fun `given long when converted to account id or null then account id created`() {
        val value = 1L

        val actual = value.toAccountIdOrNull()

        assertEquals(1L, actual?.getValue())
    }

    @Test
    fun `given null value when converted to account id or null then verify null returned`() {
        val value = null

        val actual = value.toAccountIdOrNull()

        assertNull(actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun provideValuesAccountIdValidationTest() = listOf(
            // Format: long to test, is valid
            arguments(0L, true),
            arguments(1L, true),
            arguments(Long.MAX_VALUE, true),
            arguments(-1L, false),
            arguments(Long.MIN_VALUE, false),
        )
    }
}
