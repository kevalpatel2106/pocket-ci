package com.kevalpatel2106.entity

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.id.toAccountId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class AccountTest {
    private val fixture = KFixture()

    @Test
    fun `given base url doesn't end with back slash when created check AssertionError thrown`() {
        assertThrows<AssertionError> {
            Account(
                localId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
                name = fixture(),
                type = fixture(),
                baseUrl = Url("http://example.com"),
                avatar = Url("https://${fixture<String>()}.com/"),
                email = fixture(),
                isSelected = fixture(),
                authToken = fixture(),
            )
        }
    }

    @Test
    fun `given base url ends with back slash when created check AssertionError thrown`() {
        assertDoesNotThrow {
            Account(
                localId = fixture.range(0L..Long.MAX_VALUE).toAccountId(),
                name = fixture(),
                type = fixture(),
                baseUrl = Url("http://example.com/"),
                avatar = Url("https://${fixture<String>()}.com/"),
                email = fixture(),
                isSelected = fixture(),
                authToken = fixture(),
            )
        }
    }
}
