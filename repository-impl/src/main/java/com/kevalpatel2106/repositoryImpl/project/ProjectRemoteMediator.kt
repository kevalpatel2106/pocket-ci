package com.kevalpatel2106.repositoryImpl.project

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.toToken
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.project.ProjectRepoImpl.Companion.PAGE_SIZE
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpired
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCache
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCacheImpl
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
internal class ProjectRemoteMediator private constructor(
    private val accountId: AccountId,
    private val accountDao: AccountDao,
    private val ciConnectorFactory: CIConnectorFactory,
    private val saveProjectsToCache: SaveProjectsToCache,
    private val isProjectCacheExpired: IsProjectCacheExpired,
) : RemoteMediator<Int, ProjectDto>() {

    override suspend fun initialize(): InitializeAction {
        return if (isProjectCacheExpired(accountId)) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProjectDto>,
    ): MediatorResult {
        val account = accountDao.getAccount(accountId.getValue())
        val ciRepo = ciConnectorFactory.get(account.type)

        val cursorToLoad: String? = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE_CURSOR
            LoadType.PREPEND -> {
                // Prepend is not supported
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> account.nextProjectCursor
        }

        var mediatorResult: MediatorResult = MediatorResult.Success(false)
        runCatching {
            val pagedData = ciRepo.getProjectsUpdatedDesc(
                accountId = accountId,
                url = account.baseUrl,
                token = account.token.toToken(),
                cursor = cursorToLoad,
                limit = PAGE_SIZE,
            )
            saveProjectsToCache(accountId, pagedData.data, cursorToLoad)
            accountDao.updateNextPageCursor(accountId.getValue(), pagedData.nextCursor)
            pagedData.nextCursor.isNullOrBlank()
        }.onSuccess { endOfPaginationReached ->
            mediatorResult = MediatorResult.Success(endOfPaginationReached)
        }.onFailure { error ->
            Timber.e(error)
            mediatorResult = MediatorResult.Error(error)
        }
        return mediatorResult
    }

    class Factory @Inject constructor(
        private val accountDao: AccountDao,
        private val ciConnectorFactory: CIConnectorFactory,
        private val saveProjectsToCacheImpl: SaveProjectsToCacheImpl,
        private val isProjectCacheExpired: IsProjectCacheExpired,
    ) {
        fun create(accountId: AccountId) = ProjectRemoteMediator(
            accountId,
            accountDao,
            ciConnectorFactory,
            saveProjectsToCacheImpl,
            isProjectCacheExpired,
        )
    }

    companion object {
        internal val STARTING_PAGE_CURSOR = null
    }
}
