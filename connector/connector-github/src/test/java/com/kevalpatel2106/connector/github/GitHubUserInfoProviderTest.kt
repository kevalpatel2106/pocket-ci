package com.kevalpatel2106.connector.github

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.AccountMapper
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class GitHubUserInfoProviderTest {
    private val fixture = KFixture()
    private val mappedAccount = getAccountFixture(fixture)
    private val githubEndpoint = mock<GitHubEndpoint>()
    private val retrofitClient = mock<GitHubRetrofitClient> {
        on { getGithubService(any(), any()) } doReturn githubEndpoint
    }
    private val accountMapper = mock<AccountMapper> {
        on { invoke(any(), any(), any()) } doReturn mappedAccount
    }

    private val subject = GitHubUserInfoProvider(retrofitClient, accountMapper)

    @Test
    fun `given user can be loaded successfully from network when get account called then verify result`() =
        runTest {
            val userDto = fixture<UserDto>()
            val baseUrl = getUrlFixture(fixture)
            val baseToken = fixture<Token>()
            whenever(githubEndpoint.getUser()).thenReturn(userDto)

            val actual = subject.getMyAccountInfo(baseUrl, baseToken)

            verify(accountMapper).invoke(userDto, baseUrl, baseToken)
            assertEquals(mappedAccount, actual)
        }

    @Test
    fun `given error getting user from network when get account called then exception thrown`() =
        runTest {
            whenever(githubEndpoint.getUser()).thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getMyAccountInfo(fixture(), fixture())
            }
        }
}
