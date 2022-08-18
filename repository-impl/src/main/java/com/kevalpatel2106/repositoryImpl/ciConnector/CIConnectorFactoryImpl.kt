package com.kevalpatel2106.repositoryImpl.ciConnector

import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.CIType
import javax.inject.Inject

internal class CIConnectorFactoryImpl @Inject constructor(
    private val map: Map<CIType, @JvmSuppressWildcards CIConnector>,
) : CIConnectorFactory {

    override fun get(type: CIType) =
        map[type] ?: error("Looks like CI repo for type $type not set up yet.")

    override fun getAll(): List<CIConnector> = map.values.toList()
}
