package com.kevalpatel2106.connector.github.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Account
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Token
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.toUrlOrNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class AccountMapperImplTest {
    private val fixture = KFixture()
    private val subject = AccountMapperImpl()

    // region overall entity check
    @Test
    fun `given user dto with no avatar when mapped then verify account entity`() {
        val dto = fixture<UserDto>().copy(avatarUrl = null)
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject(dto, baseUrl, token)

        val expected = Account(
            name = dto.name,
            email = dto.email,
            avatar = null,
            authToken = token,
            baseUrl = baseUrl,
            type = CIType.GITHUB,
            isSelected = false,
            localId = AccountId.EMPTY,
        )
        assertEquals(actual, expected)
    }

    @Test
    fun `given user dto with avatar when mapped then verify account entity`() {
        val dto = fixture<UserDto>().copy(avatarUrl = getUrlFixture(fixture).value)
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject(dto, baseUrl, token)

        val expected = Account(
            name = dto.name,
            email = dto.email,
            avatar = dto.avatarUrl.toUrlOrNull(),
            authToken = token,
            baseUrl = baseUrl,
            type = CIType.GITHUB,
            isSelected = false,
            localId = AccountId.EMPTY,
        )
        assertEquals(actual, expected)
    }
    // endregion

    // region checking specific constant values
    @Test
    fun `given user dto when mapped then ci type is always github`() {
        val dto = fixture<UserDto>()
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject(dto, baseUrl, token).type

        assertEquals(CIType.GITHUB, actual)
    }

    @Test
    fun `given user dto when mapped then local id is always empty`() {
        val dto = fixture<UserDto>()
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject(dto, baseUrl, token).localId

        assertEquals(AccountId.EMPTY, actual)
    }

    @Test
    fun `given user dto when mapped then selected is always false`() {
        val dto = fixture<UserDto>()
        val baseUrl = getUrlFixture(fixture)
        val token = fixture<Token>()

        val actual = subject(dto, baseUrl, token).isSelected

        assertFalse(actual)
    }
    // endregion
}
