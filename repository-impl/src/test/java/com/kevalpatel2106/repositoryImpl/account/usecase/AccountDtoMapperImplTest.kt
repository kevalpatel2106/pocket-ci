package com.kevalpatel2106.repositoryImpl.account.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.db.accountTable.AccountDto
import com.kevalpatel2106.coreTest.getAccountFixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

internal class AccountDtoMapperImplTest {
    private val fixture = KFixture()
    private val subject = AccountDtoMapperImpl()

    @Test
    fun `given account when mapped then verify dto`() {
        val account = getAccountFixture(fixture)
        val now = Date()

        val actual = subject(account, now)

        val expected = AccountDto(
            id = account.localId,
            name = account.name,
            baseUrl = account.baseUrl,
            savedAt = now,
            token = account.authToken,
            type = account.type,
            email = account.email,
            avatar = account.avatar,
            nextProjectCursor = null,
            projectCacheLastUpdated = 0,
        )
        assertEquals(actual, expected)
    }
}
