package com.kevalpatel2106.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildStatusKtTest {

    @ParameterizedTest(name = "test build status {0} is in progress {1}")
    @MethodSource("provideValues")
    fun `test build status is in progress`(buildStatus: BuildStatus, isInProgress: Boolean) {
        assertEquals(isInProgress, buildStatus.isInProgress())
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = BuildStatus.values()
            .map { arguments(it, it in listOf(BuildStatus.RUNNING, BuildStatus.PENDING)) }
    }
}
