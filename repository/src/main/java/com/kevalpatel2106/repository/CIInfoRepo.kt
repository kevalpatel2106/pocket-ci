package com.kevalpatel2106.repository

import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.id.AccountId

interface CIInfoRepo {
    suspend fun getSupportedCI(): List<CIInfo>
    suspend fun getCIInfo(ciType: CIType): CIInfo
    suspend fun getCIInfo(accountId: AccountId): CIInfo
}
