package com.kevalpatel2106.repository

import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.CIType

interface CIInfoRepo {
    suspend fun getSupportedCI(): List<CIInfo>
    suspend fun getCI(ciType: CIType): CIInfo
}
