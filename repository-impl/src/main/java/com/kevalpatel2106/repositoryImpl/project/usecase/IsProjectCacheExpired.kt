package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.entity.id.AccountId

internal interface IsProjectCacheExpired {

    suspend operator fun invoke(
        accountId: AccountId,
        nowMills: Long = System.currentTimeMillis(),
    ): Boolean
}
