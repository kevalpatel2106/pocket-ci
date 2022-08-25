package com.kevalpatel2106.connector.bitrise

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.dto.MeDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.connector.bitrise.network.mapper.AccountMapper
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Token
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class BitriseUserInfoProviderTest {
    private val fixture = KFixture()
    private val mappedAccount = getAccountFixture(fixture)
    private val githubEndpoint = mock<BitriseEndpoint>()
    private val retrofitClient = mock<BitriseRetrofitClient> {
        on { getBitriseService(any(), any()) } doReturn githubEndpoint
    }
    private val accountMapper = mock<AccountMapper> {
        on { invoke(any(), any(), any()) } doReturn mappedAccount
    }

    private val subject = BitriseUserInfoProvider(retrofitClient, accountMapper)

    @Test
    fun `given user can be loaded successfully from network when get account called then verify result`() =
        runTest {
            val meDto = fixture<MeDto>()
            val baseUrl = getUrlFixture(fixture)
            val baseToken = fixture<Token>()
            whenever(githubEndpoint.getMe()).thenReturn(ResponseDto(meDto, null, null))

            val actual = subject.getMyAccountInfo(baseUrl, baseToken)

            verify(accountMapper).invoke(meDto, baseUrl, baseToken)
            assertEquals(mappedAccount, actual)
        }

    @Test
    fun `given error getting user from network when get account called then exception thrown`() =
        runTest {
            whenever(githubEndpoint.getMe()).thenThrow(IllegalStateException())

            org.junit.jupiter.api.assertThrows<IllegalStateException> {
                subject.getMyAccountInfo(fixture(), fixture())
            }
        }
}
