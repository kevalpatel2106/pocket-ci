package com.kevalpatel2106.feature.log.usecase

import com.kevalpatel2106.feature.log.log.usecase.CalculateTextScale
import com.kevalpatel2106.feature.log.log.usecase.CalculateTextScaleImpl
import com.kevalpatel2106.feature.log.log.usecase.TextChangeDirection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class CalculateTextScaleImplTest {
    private val subject = CalculateTextScaleImpl()

    @ParameterizedTest(name = "given direction {0} when invoked then check expected scale {1}")
    @MethodSource("provideValues")
    fun `given direction when invoked then check expected scale`(
        direction: Int,
        expectedScale: Float,
    ) {
        assertEquals(expectedScale, subject(direction))
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(TextChangeDirection.TEXT_SIZE_DECREASE, 0.5f),
            arguments(TextChangeDirection.TEXT_SIZE_INCREASE, 2f),
            arguments(100, CalculateTextScale.DEFAULT_SCALE),
        )
    }
}
