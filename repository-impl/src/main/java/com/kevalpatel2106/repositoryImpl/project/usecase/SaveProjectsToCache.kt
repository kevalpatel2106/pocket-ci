package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId

interface SaveProjectsToCache {

    suspend operator fun invoke(
        accountId: AccountId,
        projects: List<Project>,
        cursorLoaded: String?,
        nowMills: Long = System.currentTimeMillis()
    )
}
