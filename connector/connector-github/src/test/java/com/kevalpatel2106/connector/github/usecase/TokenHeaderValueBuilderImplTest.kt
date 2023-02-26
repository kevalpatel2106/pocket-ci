package com.kevalpatel2106.connector.github.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TokenHeaderValueBuilderImplTest {
    private val fixture = KFixture()
    private val subject = TokenHeaderValueBuilderImpl()

    @Test
    fun `given sample token when invoked then check result`() {
        val sampleToken = fixture<Token>()

        val actual = subject(sampleToken)

        assertEquals("Bearer ${sampleToken.getValue()}", actual)
    }
}
