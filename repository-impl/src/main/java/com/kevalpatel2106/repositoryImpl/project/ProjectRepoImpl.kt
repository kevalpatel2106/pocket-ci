package com.kevalpatel2106.repositoryImpl.project

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repository.ProjectRepo
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProjectRepoImpl @Inject constructor(
    private val projectDao: ProjectDao,
    private val projectRemoteMediatorFactory: ProjectRemoteMediator.Factory,
    private val projectMapper: ProjectMapper,
) : ProjectRepo {

    override suspend fun getProject(remoteId: ProjectId, accountId: AccountId): Project? {
        return if (projectDao.getCount(remoteId.getValue(), accountId.getValue()) > 0) {
            val dao = projectDao.getProject(remoteId.getValue(), accountId.getValue())
            projectMapper(dao)
        } else {
            null
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getProjects(accountId: AccountId): Flow<PagingData<Project>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { projectDao.getProjectsUpdatedDesc(accountId.getValue()) },
            remoteMediator = projectRemoteMediatorFactory.create(accountId),
        ).flow.map { projectsDto ->
            projectsDto.map { projectMapper(it) }
        }
    }

    companion object {
        // Note: Because we store the next cursor in accounts table, changing page size will
        // require us to invalidate project cache on app update.
        internal const val PAGE_SIZE = 40
    }
}
