package com.kevalpatel2106.repositoryImpl.ciRepo

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

internal class CIConnectorFactoryImplTest {
    private val kFixture = KFixture()
    private val map: MutableMap<CIType, @JvmSuppressWildcards CIConnector> by lazy {
        val map = mutableMapOf<CIType, @JvmSuppressWildcards CIConnector>()
        CIType.values().forEach { map[it] = mock() }
        return@lazy map
    }
    private val subject = CIConnectorFactoryImpl(map)

    @Test
    fun `given ci type mapping when get ci repo called check correct ci repo returned`() {
        val ciType = kFixture<CIType>()

        assertEquals(map[ciType], subject.get(ciType))
    }

    @Test
    fun `given ci type not in mapping when get ci repo called check error`() {
        val ciType = kFixture<CIType>()
        map.remove(ciType)

        try {
            subject.get(ciType)
            fail("Exception should be thrown.")
        } catch (e: IllegalStateException) {
            assertNotNull(e.message)
        }
    }

    @Test
    fun `when get all ci info called list contains all supported ci`() {
        assertEquals(map.values.toList(), subject.getAll())
    }
}
