package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.entity.BuildStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildStatusMapperImplTest {
    private val subject = BuildStatusMapperImpl()

    @ParameterizedTest(name = "given conclusion {1} and status {0} when mapped then build status is {1}")
    @MethodSource("provideValues")
    fun `given conclusion and status when mapped then build status is expected`(
        status: String,
        conclusion: String?,
        expectedBuildStatus: BuildStatus,
    ) {
        assertEquals(expectedBuildStatus, subject(conclusion, status))
    }

    companion object {
        private val POSSIBLE_CONCLUSION =
            listOf("success", "failure", "cancelled", "skipped", null, "invalid-value")

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = mutableListOf(
            arguments("completed", "success", BuildStatus.SUCCESS),
            arguments("completed", "failure", BuildStatus.FAIL),
            arguments("completed", "cancelled", BuildStatus.ABORT),
            arguments("completed", "skipped", BuildStatus.SKIPPED),
            arguments("completed", null, BuildStatus.UNKNOWN),
            arguments("completed", "invalid-value", BuildStatus.UNKNOWN),
        ).apply {
            POSSIBLE_CONCLUSION.forEach { conclusion ->
                add(arguments("queued", conclusion, BuildStatus.PENDING))
                add(arguments("in_progress", conclusion, BuildStatus.RUNNING))
            }
        }
    }
}
