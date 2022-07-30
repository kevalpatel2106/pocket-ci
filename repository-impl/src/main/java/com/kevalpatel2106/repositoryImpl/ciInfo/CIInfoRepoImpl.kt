package com.kevalpatel2106.repositoryImpl.ciInfo

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import javax.inject.Inject

internal class CIInfoRepoImpl @Inject constructor(
    private val ciConnectorFactory: CIConnectorFactory,
) : CIInfoRepo {

    override suspend fun getSupportedCI() = ciConnectorFactory.getAll()
        .map { item -> item.getCIInfo() }

    override suspend fun getCI(ciType: CIType) = ciConnectorFactory.get(ciType).getCIInfo()
}
