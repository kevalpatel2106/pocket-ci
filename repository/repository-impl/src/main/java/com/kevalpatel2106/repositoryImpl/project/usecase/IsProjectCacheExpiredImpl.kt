package com.kevalpatel2106.repositoryImpl.project.usecase

import com.kevalpatel2106.cache.db.accountTable.AccountDao
import com.kevalpatel2106.entity.id.AccountId
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class IsProjectCacheExpiredImpl @Inject constructor(
    private val accountDao: AccountDao,
) : IsProjectCacheExpired {

    override suspend operator fun invoke(accountId: AccountId, nowMills: Long): Boolean {
        val lastCacheUpdate = accountDao.getLastProjectRefreshEpoch(accountId.getValue())
        return nowMills - lastCacheUpdate >= MAX_CACHE_AGE_MILLS
    }

    companion object {
        private val MAX_CACHE_AGE_MILLS = TimeUnit.DAYS.toMillis(1L)
    }
}
