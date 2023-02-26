package com.kevalpatel2106.repositoryImpl.account.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.repositoryImpl.getAccountDtoFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AccountMapperImplTest {
    private val fixture = KFixture()
    private val subject = AccountMapperImpl()

    @Test
    fun `given account dto and is account selected when mapped then verify account entity`() {
        val dto = getAccountDtoFixture(fixture)
        val isSelected = fixture<Boolean>()

        val actual = subject(dto, isSelected)

        val expected = com.kevalpatel2106.entity.Account(
            localId = dto.id,
            name = dto.name,
            email = dto.email,
            avatar = dto.avatar,
            type = dto.type,
            isSelected = isSelected,
            baseUrl = dto.baseUrl,
            authToken = dto.token,
        )
        assertEquals(actual, expected)
    }
}
