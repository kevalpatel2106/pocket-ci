package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.cache.db.projectTable.ProjectDao
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
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
        nowMills: Long,
    ) {
        val projectDtos = projects.map { projectDtoMapper(it) }
        if (cursorLoaded == ProjectRemoteMediator.STARTING_PAGE_CURSOR) {
            projectDao.replaceWithNewProjects(accountId.getValue(), projectDtos)
            accountDao.updateLastProjectRefreshEpoch(accountId.getValue(), nowMills)
        } else {
            projectDao.addUpdateProjects(projectDtos)
        }
    }
}
