package com.kevalpatel2106.feature.logs.usecase

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repository.JobRepo
import javax.inject.Inject

internal class LogSourceSelectorImpl @Inject constructor(
    private val buildRepo: BuildRepo,
    private val jobRepo: JobRepo,
    private val ciInfoRepo: CIInfoRepo,
    private val accountRepo: AccountRepo,
) : LogSourceSelector {

    override suspend operator fun invoke(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        jobId: JobId?,
    ): String {
        val account = accountRepo.getAccount(accountId)
        val ciInfo = ciInfoRepo.getCI(account.type)

        return when {
            jobId != null && ciInfo.supportJobLogs -> {
                jobRepo.getJobLogs(accountId, projectId, buildId, jobId)
            }
            jobId == null && ciInfo.supportBuildLogs -> {
                buildRepo.getBuildLogs(accountId, projectId, buildId)
            }
            else -> error(
                "Cannot get build logs for ${ciInfo.type.name} with buildId $buildId and " +
                        "job ID $jobId. Required field missing or feature not supported.",
            )
        }
    }
}