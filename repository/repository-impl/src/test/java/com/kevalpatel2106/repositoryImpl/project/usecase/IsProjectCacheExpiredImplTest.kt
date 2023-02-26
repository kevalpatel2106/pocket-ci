package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.coreTest.getAccountIdFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.concurrent.TimeUnit

internal class IsProjectCacheExpiredImplTest {
    private val fixture = KFixture()
    private val accountDao = mock<AccountDao>()
    private val nowMills = System.currentTimeMillis()
    private val subject = IsProjectCacheExpiredImpl(accountDao)

    @Test
    fun `given last project update one day ago when invoked then return cache expired`() = runTest {
        whenever(accountDao.getLastProjectRefreshEpoch(any()))
            .thenReturn(nowMills - TimeUnit.DAYS.toMillis(1L) - 1000L)

        val actual = subject(getAccountIdFixture(fixture), nowMills)

        assertTrue(actual)
    }

    @Test
    fun `given last project update less than one day ago when invoked then return not cache expired`() =
        runTest {
            whenever(accountDao.getLastProjectRefreshEpoch(any()))
                .thenReturn(nowMills - TimeUnit.DAYS.toMillis(1L) + 1000L)

            val actual = subject(getAccountIdFixture(fixture), nowMills)

            assertFalse(actual)
        }

    @Test
    fun `given last project update 0 when invoked then return not cache expired`() = runTest {
        whenever(accountDao.getLastProjectRefreshEpoch(any())).thenReturn(0)

        val actual = subject(getAccountIdFixture(fixture), nowMills)

        assertTrue(actual)
    }
}
