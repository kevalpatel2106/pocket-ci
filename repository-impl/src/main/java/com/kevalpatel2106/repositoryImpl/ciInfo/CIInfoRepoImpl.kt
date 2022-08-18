package com.kevalpatel2106.repositoryImpl.ciInfo

import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repositoryImpl.cache.db.accountTable.AccountDao
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import javax.inject.Inject

internal class CIInfoRepoImpl @Inject constructor(
    private val ciConnectorFactory: CIConnectorFactory,
    private val accountDao: AccountDao,
) : CIInfoRepo {

    override suspend fun getSupportedCI() = ciConnectorFactory.getAll()
        .map { item -> item.getCIInfo() }

    override suspend fun getCIInfo(ciType: CIType) = ciConnectorFactory.get(ciType).getCIInfo()

    override suspend fun getCIInfo(accountId: AccountId): CIInfo {
        val accountDto = accountDao.getAccountBasic(accountId.getValue())
        return getCIInfo(accountDto.type)
    }
}
