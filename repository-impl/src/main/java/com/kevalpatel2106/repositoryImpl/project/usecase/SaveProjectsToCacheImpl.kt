package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.repositoryImpl.project.ProjectRemoteMediator
import javax.inject.Inject

internal class SaveProjectsToCacheImpl @Inject constructor(
    private val accountDao: AccountDao,
    private val projectDao: ProjectDao,
    private val projectDtoMapper: ProjectDtoMapper,
) : SaveProjectsToCache {

    override suspend operator fun invoke(
        accountId: AccountId,
        projects: List<Project>,
        cursorLoaded: String?,
        nowMills: Long
    ) {
        if (cursorLoaded == ProjectRemoteMediator.STARTING_PAGE_CURSOR) {
            projectDao.deleteProjects(accountId.getValue())
            accountDao.updateLastProjectRefreshEpoch(accountId.getValue(), nowMills)
        }
        projectDao.addUpdateProjects(projects.map { projectDtoMapper(it) })
    }
}
