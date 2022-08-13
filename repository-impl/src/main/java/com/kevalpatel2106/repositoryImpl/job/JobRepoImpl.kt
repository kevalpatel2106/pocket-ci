package com.kevalpatel2106.repositoryImpl.job

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.repository.JobRepo
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class JobRepoImpl @Inject constructor(
    private val ciConnectorFactory: CIConnectorFactory,
    private val projectDao: ProjectDao,
    private val accountDao: AccountDao,
    private val pagingSourceFactory: JobsPagingSource.Factory,
) : JobRepo {

    override fun getJobs(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
    ): Flow<PagingData<Job>> = flow {
        val accountDto = accountDao.getAccount(accountId.getValue())
        val projectDto = projectDao.getProject(projectId.getValue(), accountId.getValue())
        emit(Triple(accountDto, projectDto, buildId))
    }.flatMapLatest { (accountDto, projectDto, buildId) ->
        Pager(config = PagingConfig(pageSize = BuildRepoImpl.PAGE_SIZE)) {
            pagingSourceFactory.create(
                buildId = buildId,
                accountDto = accountDto,
                projectDto = projectDto,
                ciConnector = ciConnectorFactory.get(accountDto.type),
            )
        }.flow
    }

    override suspend fun getJobLogs(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        jobId: JobId,
    ): String {
        val accountDto = accountDao.getAccount(accountId.getValue())
        val ciConnector = ciConnectorFactory.get(accountDto.type)
        val projectDto = projectDao.getProject(projectId.getValue(), accountId.getValue())
        return ciConnector.getJobLogs(
            url = accountDto.baseUrl,
            token = accountDto.token.toToken(),
            projectId = projectId,
            projectOwner = projectDto.owner,
            projectName = projectDto.name,
            buildId = buildId,
            jobId = jobId,
        )
    }
}
