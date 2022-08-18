package com.kevalpatel2106.repositoryImpl.job

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.JobRepo
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountBasicMapper
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectBasicMapper
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class JobRepoImpl @Inject constructor(
    private val ciConnectorFactory: CIConnectorFactory,
    private val projectDao: ProjectDao,
    private val accountDao: AccountDao,
    private val projectBasicMapper: ProjectBasicMapper,
    private val accountBasicMapper: AccountBasicMapper,
    private val pagingSourceFactory: JobsPagingSource.Factory,
) : JobRepo {

    override fun getJobs(accountId: AccountId, projectId: ProjectId, buildId: BuildId) = flow {
        val accountDto = accountDao.getAccountBasic(accountId.getValue())
        val projectDto = projectDao.getProjectBasic(projectId.getValue(), accountId.getValue())
        emit(Triple(accountDto, projectDto, buildId))
    }.flatMapLatest { (accountDto, projectDto, buildId) ->
        Pager(config = PagingConfig(pageSize = BuildRepoImpl.PAGE_SIZE)) {
            pagingSourceFactory.create(
                buildId = buildId,
                accountBasic = accountBasicMapper(accountDto),
                projectBasic = projectBasicMapper(projectDto),
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
        val accountDto = accountDao.getAccountBasic(accountId.getValue())
        val projectDto = projectDao.getProjectBasic(projectId.getValue(), accountId.getValue())
        val ciConnector = ciConnectorFactory.get(accountDto.type)
        return ciConnector.getJobLogs(
            accountBasic = accountBasicMapper(accountDto),
            projectBasic = projectBasicMapper(projectDto),
            buildId = buildId,
            jobId = jobId,
        )
    }
}
