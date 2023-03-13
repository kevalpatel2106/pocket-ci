package com.kevalpatel2106.feature.log.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountIdFixture
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.feature.log.log.usecase.LogSourceSelectorImpl
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repository.JobRepo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class LogSourceSelectorImplTest {
    private val fixture = KFixture()
    private val ciInfo = fixture<CIInfo>()
    private val accountId = getAccountIdFixture(fixture)
    private val projectId = fixture<ProjectId>()
    private val buildId = fixture<BuildId>()
    private val jobId = fixture<JobId>()
    private val buildRepo = mock<BuildRepo>()
    private val jobRepo = mock<JobRepo>()
    private val ciInfoRepo = mock<CIInfoRepo>()

    private val subject = LogSourceSelectorImpl(buildRepo, jobRepo, ciInfoRepo)

    @Test
    fun `given ci supports job logs and job id is not null when invoked then job logs downloads`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportJobLevelLogs = true, supportBuildLevelLogs = false))

            subject(accountId, projectId, buildId, jobId)

            verify(jobRepo).getJobLogs(accountId, projectId, buildId, jobId)
        }

    @Test
    fun `given ci supports job logs and job id is null when invoked then then error thrown`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportJobLevelLogs = true, supportBuildLevelLogs = false))

            assertThrows<IllegalStateException> {
                subject(accountId, projectId, buildId, null)
            }
        }

    @Test
    fun `given ci supports build logs and job id is null when invoked then build logs downloads`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportBuildLevelLogs = true, supportJobLevelLogs = false))

            subject(accountId, projectId, buildId, null)

            verify(buildRepo).getBuildLogs(accountId, projectId, buildId)
        }

    @Test
    fun `given ci supports build logs and job logs and job id is not null when invoked then job logs downloads`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportBuildLevelLogs = true, supportJobLevelLogs = true))

            subject(accountId, projectId, buildId, jobId)

            verify(jobRepo).getJobLogs(accountId, projectId, buildId, jobId)
        }

    @Test
    fun `given ci supports build logs and job logs and job id is null when invoked then build logs downloads`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportBuildLevelLogs = true, supportJobLevelLogs = true))

            subject(accountId, projectId, buildId, null)

            verify(buildRepo).getBuildLogs(accountId, projectId, buildId)
        }

    @Test
    fun `given not ci support build logs and job logs when invoked then error thrown`() =
        runTest {
            whenever(ciInfoRepo.getCIInfo(any<AccountId>()))
                .thenReturn(ciInfo.copy(supportBuildLevelLogs = false, supportJobLevelLogs = false))

            assertThrows<IllegalStateException> {
                subject(accountId, projectId, buildId, jobId)
            }
        }
}
