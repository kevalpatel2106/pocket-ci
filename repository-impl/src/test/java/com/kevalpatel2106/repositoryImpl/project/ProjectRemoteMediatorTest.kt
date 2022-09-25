package com.kevalpatel2106.repositoryImpl.project

import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.MediatorResult
import androidx.paging.RemoteMediator.MediatorResult.Success
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.getAccountDtoFixture
import com.kevalpatel2106.repositoryImpl.getPagingStateFixture
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpired
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCache
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class ProjectRemoteMediatorTest {
    private val fixture = KFixture()
    private val accountId = getAccountIdFixture(fixture)
    private val account = getAccountDtoFixture(fixture)
    private val accountDao = mock<AccountDao>()
    private val ciConnector = mock<CIConnector>()
    private val ciConnectorFactory = mock<CIConnectorFactory> {
        on { get(any()) } doReturn ciConnector
    }
    private val saveProjectsToCache = mock<SaveProjectsToCache>()
    private val isProjectCacheExpired = mock<IsProjectCacheExpired>()

    private val subject = ProjectRemoteMediator.Factory(
        accountDao,
        ciConnectorFactory,
        saveProjectsToCache,
        isProjectCacheExpired,
    ).create(accountId)

    @BeforeEach
    fun before() = runTest {
        whenever(accountDao.getAccount(accountId.getValue()))
            .thenReturn(account.copy(id = accountId))
    }

    // region initialize
    @Test
    fun `given project cache expired when initialised then initialisation action is refresh`() =
        runTest {
            whenever(isProjectCacheExpired(any(), any())).thenReturn(true)

            val actual = subject.initialize()

            assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, actual)
        }

    @Test
    fun `given project cache not expired when initialised then initialisation action is skip refresh`() =
        runTest {
            whenever(isProjectCacheExpired(any(), any())).thenReturn(false)

            val actual = subject.initialize()

            assertEquals(RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH, actual)
        }
    // endregion

    // region load
    @Test
    fun `given load type prepend when page load then check result is success and end of pagination reached`() =
        runTest {
            val actual = subject.load(LoadType.PREPEND, getPagingStateFixture(fixture))

            assertTrue(actual is Success)
            assertTrue((actual as Success).endOfPaginationReached)
        }

    @Test
    fun `given load type refresh when page load then first page loaded from network and saved to cache`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = "cursor",
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)

            subject.load(LoadType.REFRESH, getPagingStateFixture(fixture))

            inOrder(ciConnector, accountDao, saveProjectsToCache) {
                verify(ciConnector).getProjectsUpdatedDesc(
                    any(),
                    eq(null),
                    eq(ProjectRepoImpl.PAGE_SIZE),
                )
                saveProjectsToCache(accountId, pageData.data, null)
                accountDao.updateNextPageCursor(accountId.getValue(), pageData.nextCursor)
            }
        }

    @Test
    fun `given load type append when page load then first page loaded from network and saved to cache`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = "cursor",
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)

            subject.load(LoadType.APPEND, getPagingStateFixture(fixture))

            inOrder(ciConnector, accountDao, saveProjectsToCache) {
                verify(ciConnector).getProjectsUpdatedDesc(
                    any(),
                    eq(account.nextProjectCursor),
                    eq(ProjectRepoImpl.PAGE_SIZE),
                )
                saveProjectsToCache(accountId, pageData.data, account.nextProjectCursor)
                accountDao.updateNextPageCursor(accountId.getValue(), pageData.nextCursor)
            }
        }

    @Test
    fun `given load type append the loaded page is last when page load check result success with end of pagination`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = null,
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)

            val actual = subject.load(LoadType.APPEND, getPagingStateFixture(fixture))

            assertTrue(actual is Success)
            assertTrue((actual as Success).endOfPaginationReached)
        }

    @Test
    fun `given load type refresh the loaded page is last when page load check result success with end of pagination`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = null,
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)

            val actual = subject.load(LoadType.REFRESH, getPagingStateFixture(fixture))

            assertTrue(actual is Success)
            assertTrue((actual as Success).endOfPaginationReached)
        }

    @Test
    fun `given page load from network fails when page load check error result`() =
        runTest {
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenThrow(IllegalStateException())

            val actual = subject.load(LoadType.REFRESH, getPagingStateFixture(fixture))

            assertTrue(actual is MediatorResult.Error)
        }

    @Test
    fun `given saving page to db fails when page load check error result`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = fixture(),
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)
            whenever(saveProjectsToCache(any(), any(), anyOrNull(), any()))
                .thenThrow(IllegalStateException())

            val actual = subject.load(LoadType.REFRESH, getPagingStateFixture(fixture))

            assertTrue(actual is MediatorResult.Error)
        }

    @Test
    fun `given saving next page cursor to db fails when page load check error result`() =
        runTest {
            val pageData = PagedData(
                data = listOf(getProjectFixture(fixture)),
                nextCursor = fixture(),
            )
            whenever(ciConnector.getProjectsUpdatedDesc(any(), anyOrNull(), any()))
                .thenReturn(pageData)
            whenever(accountDao.updateNextPageCursor(any(), any()))
                .thenThrow(IllegalStateException())

            val actual = subject.load(LoadType.REFRESH, getPagingStateFixture(fixture))

            assertTrue(actual is MediatorResult.Error)
        }
    // endregion
}
