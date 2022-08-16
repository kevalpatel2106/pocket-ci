package com.kevalpatel2106.repositoryImpl.artifact

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.ArtifactRepo
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ArtifactRepoImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val accountDao: AccountDao,
    private val ciConnectorFactory: CIConnectorFactory,
    private val pagingSourceFactory: ArtifactsPagingSource.Factory,
) : ArtifactRepo {

    override fun getArtifacts(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
    ) = flow {
        val projectDto = projectDao.getProject(projectId.getValue(), accountId.getValue())
        val accountDto = accountDao.getAccount(accountId.getValue())
        emit(accountDto to projectDto)
    }.flatMapLatest { (accountDto, projectDto) ->
        Pager(config = PagingConfig(pageSize = BuildRepoImpl.PAGE_SIZE)) {
            pagingSourceFactory.create(
                accountDto = accountDto,
                projectDto = projectDto,
                buildId = buildId,
                ciConnector = ciConnectorFactory.get(accountDto.type),
            )
        }.flow
    }
}
