package com.kevalpatel2106.repositoryImpl.artifact

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.entity.ArtifactDownloadData
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ArtifactId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.ArtifactRepo
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountBasicMapper
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectBasicMapper
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ArtifactRepoImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val accountDao: AccountDao,
    private val ciConnectorFactory: CIConnectorFactory,
    private val projectBasicMapper: ProjectBasicMapper,
    private val accountBasicMapper: AccountBasicMapper,
) : ArtifactRepo {

    override fun getArtifacts(accountId: AccountId, projectId: ProjectId, buildId: BuildId) = flow {
        val projectDto = projectDao.getProjectBasic(projectId.getValue(), accountId.getValue())
        val accountDto = accountDao.getAccountBasic(accountId.getValue())
        emit(accountDto to projectDto)
    }.flatMapLatest { (accountDto, projectDto) ->
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            ArtifactsPagingSource(
                accountBasic = accountBasicMapper(accountDto),
                projectBasic = projectBasicMapper(projectDto),
                buildId = buildId,
                ciConnector = ciConnectorFactory.get(accountDto.type),
            )
        }.flow
    }

    override suspend fun getArtifactDownloadUrl(
        accountId: AccountId,
        projectId: ProjectId,
        buildId: BuildId,
        artifactId: ArtifactId,
    ): ArtifactDownloadData {
        val accountDto = accountDao.getAccountBasic(accountId.getValue())
        val projectDto = projectDao.getProjectBasic(projectId.getValue(), accountId.getValue())
        return ciConnectorFactory.get(accountDto.type)
            .getArtifactDownloadUrl(
                projectBasic = projectBasicMapper(projectDto),
                buildId = buildId,
                artifactId = artifactId,
                accountBasic = accountBasicMapper(accountDto),
            )
    }

    companion object {
        internal const val PAGE_SIZE = 40
    }
}
