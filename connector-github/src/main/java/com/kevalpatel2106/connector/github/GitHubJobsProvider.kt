package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.connector.github.extension.executePaginatedApiCall
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.mapper.JobMapper
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.ProjectBasic
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_GONE
import javax.inject.Inject

internal class GitHubJobsProvider @Inject constructor(
    private val retrofitClient: GitHubRetrofitClient,
    private val jobMapper: JobMapper,
) : CIJobsProvider {

    override suspend fun getJobs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        cursor: String?,
        limit: Int,
    ) = executePaginatedApiCall(
        currentCursor = cursor,
        executeApiCall = { currentPage ->
            retrofitClient
                .getGithubService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
                .getJobs(
                    owner = projectBasic.owner,
                    repo = projectBasic.name,
                    buildId = buildId.getValue(),
                    perPage = limit,
                    page = currentPage,
                )
        },
        pagedDataMapper = { jobsDto -> jobsDto.jobs.map { jobMapper(it, buildId) } },
    )

    override suspend fun getJobLogs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        jobId: JobId,
    ): String {
        val client = retrofitClient.getGithubService(
            baseUrl = accountBasic.baseUrl,
            token = accountBasic.authToken,
        )
        var logs = ""
        runCatching { client.getJobLogs(projectBasic.owner, projectBasic.name, jobId.getValue()) }
            .onFailure { error ->
                val areLogsDeleted = (error as? HttpException)?.code() == HTTP_GONE
                if (!areLogsDeleted) throw error
            }
            .onSuccess { logs = it }
        return logs
    }
}
