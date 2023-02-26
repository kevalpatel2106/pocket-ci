package com.kevalpatel2106.repositoryImpl.ciConnector

import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.CIType

internal interface CIConnectorFactory {
    fun get(type: CIType): CIConnector
    fun getAll(): List<CIConnector>
}
