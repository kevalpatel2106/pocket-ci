package com.kevalpatel2106.connector.ci.internal

import com.kevalpatel2106.entity.CIInfo

interface CIInfoProvider {

    suspend fun getCIInfo(): CIInfo
}
