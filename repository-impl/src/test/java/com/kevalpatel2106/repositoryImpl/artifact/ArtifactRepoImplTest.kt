package com.kevalpatel2106.repositoryImpl.artifact

import app.cash.turbine.test
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.connector.github.network.dto.JobDto
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.coreTest.getArtifactIdFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.coreTest.getProjectIdFixture
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountBasicMapper
import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.getAccountBasicDtoFixture
import com.kevalpatel2106.repositoryImpl.getProjectBasicDtoFixture
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectBasicMapper
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ArtifactRepoImplTest {
    private val fixture = KFixture()
    private val accountBasic = getAccountBasicFixture(fixture)
    private val projectBasic = getProjectBasicFixture(fixture)
    private val accountBasicDto = getAccountBasicDtoFixture(fixture)
    private val projectBasicDto = getProjectBasicDtoFixture(fixture)
    private val ciConnector = mock<CIConnector>()

    private val ciConnectorFactory = mock<CIConnectorFactory> {
        on { get(any()) } doReturn ciConnector
    }
    private val projectDao = mock<ProjectDao>()
    private val accountDao = mock<AccountDao>()
    private val projectBasicMapper = mock<ProjectBasicMapper> {
        on { invoke(any()) } doReturn projectBasic
    }
    private val accountBasicMapper = mock<AccountBasicMapper> {
        on { invoke(any()) } doReturn accountBasic
    }

    private val subject = ArtifactRepoImpl(
        projectDao,
        accountDao,
        ciConnectorFactory,
        projectBasicMapper,
        accountBasicMapper,
    )

    @BeforeEach
    fun before() = runTest {
        whenever(projectDao.getProjectBasic(any(), any())).thenReturn(projectBasicDto)
        whenever(accountDao.getAccountBasic(any())).thenReturn(accountBasicDto)
    }

    // region getJobs
    @Test
    fun `given error reading account from db when getting artifacts then verify error thrown`() =
        runTest {
            val buildId = getBuildIdFixture(fixture)
            whenever(accountDao.getAccountBasic(any())).thenThrow(IllegalStateException())

            subject.getArtifacts(
                getAccountIdFixture(fixture),
                getProjectIdFixture(fixture),
                buildId,
            ).test {
                assertTrue(awaitError() is IllegalStateException)
                assertTrue(awaitAll<List<JobDto>>().isEmpty())
            }
        }

    @Test
    fun `given error reading project from db when getting artifacts then verify error thrown`() =
        runTest {
            val buildId = getBuildIdFixture(fixture)
            whenever(projectDao.getProjectBasic(any(), any())).thenThrow(IllegalStateException())

            subject.getArtifacts(
                getAccountIdFixture(fixture),
                getProjectIdFixture(fixture),
                buildId,
            ).test {
                assertTrue(awaitError() is IllegalStateException)
                assertTrue(awaitAll<List<JobDto>>().isEmpty())
            }
        }
    // endregion

    // region getJobLogs
    @Test
    fun `given artifact download url can fetched successfully from network when getting artifact download url then verify url download call`() =
        runTest {
            val buildId = getBuildIdFixture(fixture)
            val artifactId = getArtifactIdFixture(fixture)
            whenever(ciConnector.getArtifactDownloadUrl(any(), any(), any(), any()))
                .thenReturn(fixture())

            subject.getArtifactDownloadUrl(
                getAccountIdFixture(fixture),
                getProjectIdFixture(fixture),
                buildId,
                artifactId,
            )

            verify(ciConnector).getArtifactDownloadUrl(
                projectBasic,
                accountBasic,
                buildId,
                artifactId,
            )
        }

    @Test
    fun `given error reading account from db when getting artifact download url then verify error thrown`() =
        runTest {
            whenever(accountDao.getAccountBasic(any())).thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getArtifactDownloadUrl(
                    getAccountIdFixture(fixture),
                    getProjectIdFixture(fixture),
                    getBuildIdFixture(fixture),
                    getArtifactIdFixture(fixture),
                )
            }
        }

    @Test
    fun `given error reading project from db when getting artifact download url then verify error thrown`() =
        runTest {
            whenever(projectDao.getProjectBasic(any(), any())).thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getArtifactDownloadUrl(
                    getAccountIdFixture(fixture),
                    getProjectIdFixture(fixture),
                    getBuildIdFixture(fixture),
                    getArtifactIdFixture(fixture),
                )
            }
        }

    @Test
    fun `given error downloading logs from network when getting artifact download url then verify error thrown`() =
        runTest {
            whenever(ciConnector.getArtifactDownloadUrl(any(), any(), any(), any()))
                .thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getArtifactDownloadUrl(
                    getAccountIdFixture(fixture),
                    getProjectIdFixture(fixture),
                    getBuildIdFixture(fixture),
                    getArtifactIdFixture(fixture),
                )
            }
        }
    // endregion
}
