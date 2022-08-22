package com.kevalpatel2106.repositoryImpl.project.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.getProjectDtoFixture
import com.kevalpatel2106.repositoryImpl.project.ProjectRemoteMediator
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

internal class SaveProjectsToCacheImplTest {
    private val fixture = KFixture()
    private val accountDao = mock<AccountDao>()
    private val projectDao = mock<ProjectDao>()
    private val projectDtoMapper = mock<ProjectDtoMapper> {
        on { invoke(any(), any()) } doReturn getProjectDtoFixture(fixture)
    }
    private val nowMills = System.currentTimeMillis()
    private val subject = SaveProjectsToCacheImpl(
        accountDao,
        projectDao,
        projectDtoMapper,
    )

    @Test
    fun `given cursor first page when saved then projects and accounts deleted before saving`() =
        runTest {
            val accountId = getAccountIdFixture(fixture)
            val projects = listOf(getProjectFixture(fixture), getProjectFixture(fixture))
            val cursorLoaded = ProjectRemoteMediator.STARTING_PAGE_CURSOR

            subject(accountId, projects, cursorLoaded, nowMills)

            inOrder(projectDao, accountDao) {
                this.verify(projectDao).deleteProjects(accountId.getValue())
                this.verify(accountDao).updateLastProjectRefreshEpoch(accountId.getValue(), nowMills)
                this.verify(projectDao).addUpdateProjects(check { assertEquals(it.size, projects.size) })
            }
        }

    @Test
    fun `given cursor of next page when saved then projects and accounts deleted before saving`() =
        runTest {
            val accountId = getAccountIdFixture(fixture)
            val projects = listOf(getProjectFixture(fixture), getProjectFixture(fixture))
            val cursorLoaded = fixture<String>()

            subject(accountId, projects, cursorLoaded, nowMills)

            verify(projectDao, never()).deleteProjects(any())
            verify(accountDao, never()).updateLastProjectRefreshEpoch(any(), any())
            verify(projectDao).addUpdateProjects(check { assertEquals(it.size, projects.size) })
        }
}
