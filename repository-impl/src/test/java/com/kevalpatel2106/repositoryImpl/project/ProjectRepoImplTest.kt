package com.kevalpatel2106.repositoryImpl.project

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.db.projectLocalDataTable.ProjectLocalDataDao
import com.kevalpatel2106.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.repositoryImpl.getProjectWithLocalDataDtoFixture
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectWithLocalDataMapper
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ProjectRepoImplTest {
    private val fixture = KFixture()
    private val projectDto = getProjectWithLocalDataDtoFixture(fixture)
    private val project = getProjectFixture(fixture)
    private val projectDao = mock<ProjectDao>()
    private val projectLocalDataDao = mock<ProjectLocalDataDao>()
    private val remoteMediatorFactory =
        ProjectRemoteMediator.Factory(mock(), mock(), mock(), mock())
    private val projectWithLocalDataMapper = mock<ProjectWithLocalDataMapper> {
        on { invoke(any()) } doReturn project
    }
    private val subject = ProjectRepoImpl(
        projectDao,
        projectLocalDataDao,
        remoteMediatorFactory,
        projectWithLocalDataMapper,
    )

    @Test
    fun `given project stored when getting project then verify project read from db`() = runTest {
        val remoteId = getProjectIdFixture(fixture)
        val accountId = getAccountIdFixture(fixture)
        whenever(projectDao.getCount(any(), any())).thenReturn(1)

        subject.getProject(remoteId, accountId)

        inOrder(projectDao) {
            verify(projectDao).getCount(remoteId.getValue(), accountId.getValue())
            verify(projectDao).getProject(remoteId.getValue(), accountId.getValue())
        }
    }

    @Test
    fun `given project stored when getting project then verify project returned`() = runTest {
        val remoteId = getProjectIdFixture(fixture)
        val accountId = getAccountIdFixture(fixture)
        whenever(projectDao.getProject(any(), any())).thenReturn(projectDto)
        whenever(projectDao.getCount(any(), any())).thenReturn(1)

        val actual = subject.getProject(remoteId, accountId)

        assertEquals(project, actual)
    }

    @Test
    fun `given no project stored when getting project then verify null returned`() = runTest {
        val remoteId = getProjectIdFixture(fixture)
        val accountId = getAccountIdFixture(fixture)
        whenever(projectDao.getCount(any(), any())).thenReturn(0)

        val actual = subject.getProject(remoteId, accountId)

        assertNull(actual)
    }

    @Test
    fun `given project when project pinned then verify project local data updated`() = runTest {
        val remoteId = getProjectIdFixture(fixture)
        val accountId = getAccountIdFixture(fixture)

        subject.pinProject(remoteId, accountId)

        verify(projectLocalDataDao).updateLocalData(
            check {
                assertEquals(remoteId, it.remoteId)
                assertEquals(accountId, it.accountId)
                assertTrue(it.isPinned)
            },
        )
    }

    @Test
    fun `given project when project unpinned then verify project local data updated`() = runTest {
        val remoteId = getProjectIdFixture(fixture)
        val accountId = getAccountIdFixture(fixture)

        subject.unpinProject(remoteId, accountId)

        verify(projectLocalDataDao).updateLocalData(
            check {
                assertEquals(remoteId, it.remoteId)
                assertEquals(accountId, it.accountId)
                assertFalse(it.isPinned)
            },
        )
    }
}
