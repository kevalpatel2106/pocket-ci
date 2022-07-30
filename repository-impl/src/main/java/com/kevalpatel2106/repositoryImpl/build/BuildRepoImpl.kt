package com.kevalpatel2106.repositoryImpl.build

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class BuildRepoImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val accountDao: AccountDao,
    private val ciConnectorFactory: CIConnectorFactory,
    private val pagingSourceFactory: BuildsPagingSource.Factory,
) : BuildRepo {

    override fun getBuilds(accountId: AccountId, projectId: ProjectId): Flow<PagingData<Build>> =
        flow {
            val projectDto = projectDao.getProject(projectId.getValue(), accountId.getValue())
            val accountDto = accountDao.getAccount(accountId.getValue())
            emit(accountDto to projectDto)
        }.flatMapLatest { (accountDto, projectDto) ->
            Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
                pagingSourceFactory.create(
                    accountDto = accountDto,
                    projectDto = projectDto,
                    ciConnector = ciConnectorFactory.get(accountDto.type),
                )
            }.flow
        }

    companion object {
        internal const val PAGE_SIZE = 40
    }
}
