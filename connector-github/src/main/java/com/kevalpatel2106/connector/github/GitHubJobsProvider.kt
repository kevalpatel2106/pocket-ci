package com.kevalpatel2106.connector.github

import com.kevalpatel2106.connector.ci.internal.CIJobsProvider
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.JobMapper
import com.kevalpatel2106.entity.AccountBasic
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.PagedData
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
    ): PagedData<Job> {
        val pageNumber = cursor?.toInt() ?: GitHubEndpoint.FIRST_PAGE_CURSOR

        val jobsDto = retrofitClient
            .getService(baseUrl = accountBasic.baseUrl, token = accountBasic.authToken)
            .getJobs(projectBasic.owner, projectBasic.name, buildId.getValue(), limit, pageNumber)

        val nextCursor = if (jobsDto.jobs.isEmpty()) {
            null
        } else {
            pageNumber + 1
        }
        return PagedData(
            data = jobsDto.jobs.map { jobMapper(it, buildId) },
            nextCursor = nextCursor?.toString(),
        )
    }

    override suspend fun getJobLogs(
        accountBasic: AccountBasic,
        projectBasic: ProjectBasic,
        buildId: BuildId,
        jobId: JobId,
    ): String {
        val client = retrofitClient.getService(
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
