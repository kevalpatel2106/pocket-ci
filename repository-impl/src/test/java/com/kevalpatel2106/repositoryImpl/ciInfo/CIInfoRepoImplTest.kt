package com.kevalpatel2106.repositoryImpl.ciInfo

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.getAccountBasicDtoFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class CIInfoRepoImplTest {
    private val fixture = KFixture()
    private val bitriseInfo = fixture<CIInfo>().copy(type = CIType.BITRISE)
    private val githubInfo = fixture<CIInfo>().copy(type = CIType.GITHUB)
    private val bitriseConnector = mock<CIConnector>()
    private val githubConnector = mock<CIConnector>()
    private val ciConnectorFactory = mock<CIConnectorFactory> {
        on { getAll() } doReturn listOf(bitriseConnector, githubConnector)
        on { get(CIType.BITRISE) } doReturn bitriseConnector
        on { get(CIType.GITHUB) } doReturn githubConnector
    }
    private val accountDao = mock<AccountDao>()
    private val subject = CIInfoRepoImpl(ciConnectorFactory, accountDao)

    @BeforeEach
    fun before() = runTest {
        whenever(bitriseConnector.getCIInfo()).thenReturn(bitriseInfo)
        whenever(githubConnector.getCIInfo()).thenReturn(githubInfo)
    }

    @Test
    fun `given ci connectors when getting Bitrise ci info then verify ci info`() = runTest {
        val actual = subject.getCIInfo(CIType.BITRISE)

        assertEquals(actual, bitriseInfo)
    }

    @Test
    fun `given ci connectors when getting all ci info then verify ci info`() = runTest {
        val actual = subject.getSupportedCI()

        assertEquals(bitriseInfo, actual[0])
        assertEquals(githubInfo, actual[1])
    }

    @Test
    fun `given account id when getting ci info for account then verify ci info`() = runTest {
        val account = getAccountBasicDtoFixture(fixture).copy(type = CIType.BITRISE)
        whenever(accountDao.getAccountBasic(account.id.getValue())).thenReturn(account)

        val actual = subject.getCIInfo(account.id)

        assertEquals(actual, bitriseInfo)
    }
}
