package com.kevalpatel2106.repositoryImpl.cache.db.converter

import com.kevalpatel2106.entity.CIType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class CITypeConverterTest {

    @ParameterizedTest(name = "given ci type {1} when converted from ci type check integer key is {0}")
    @MethodSource("provideValues")
    fun `given ci type when converted from ci type check integer key`(
        intValue: Int,
        ciType: CIType,
    ) {
        assertEquals(intValue, CITypeConverter.fromCIType(ciType))
    }

    @ParameterizedTest(name = "given integer key {0} when converted to ci type check CI type is {1}")
    @MethodSource("provideValues")
    fun `given integer key when converted to ci type check ci type`(
        intValue: Int,
        ciType: CIType,
    ) {
        assertEquals(ciType, CITypeConverter.toCiType(intValue))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = CIType.values().map { arrayOf(it.id, it) }
    }
}
