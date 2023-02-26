package com.kevalpatel2106.feature.log

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.feature.log.BuildLogViewState.Empty
import com.kevalpatel2106.feature.log.BuildLogViewState.Error
import com.kevalpatel2106.feature.log.BuildLogViewState.Loading
import com.kevalpatel2106.feature.log.BuildLogViewState.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildLogViewStateTest {

    @ParameterizedTest(name = "given view state {0} when created then flipper position is {1}")
    @MethodSource("provideValues")
    fun `given view state when created then flipper position`(
        inputViewState: BuildLogViewState,
        expectedViewFlipperPos: Int,
    ) {
        assertEquals(expectedViewFlipperPos, inputViewState.viewFlipperPos)
    }

    companion object {
        private val fixture = KFixture()

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: input view state, expected view flipper pos
            arguments(Loading, 0),
            arguments(fixture<Success>(), 1),
            arguments(fixture<Error>(), 2),
            arguments(Empty, 3),
        )
    }
}
