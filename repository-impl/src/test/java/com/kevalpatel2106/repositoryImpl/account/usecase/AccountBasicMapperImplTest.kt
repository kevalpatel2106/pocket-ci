package com.kevalpatel2106.repositoryImpl.account.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.repositoryImpl.getAccountBasicDtoFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AccountBasicMapperImplTest {
    private val fixture = KFixture()
    private val subject = AccountBasicMapperImpl()

    @Test
    fun `given dto when mapped then verify entity`() {
        val dto = getAccountBasicDtoFixture(fixture)

        val actual = subject(dto)

        val expected = AccountBasic(
            localId = dto.id,
            authToken = dto.token,
            baseUrl = dto.baseUrl,
            type = dto.type,
        )
        assertEquals(actual, expected)
    }
}
