package com.kevalpatel2106.connector.bitrise.network.mapper

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.BuildStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class BuildStatusMapperImplTest {
    private val subject = BuildStatusMapperImpl()

    @ParameterizedTest(name = "given build on hold {0} and status {1} when mapped then build status is {2}")
    @MethodSource("provideValues")
    fun `check build status`(
        isOnHold: Boolean,
        buildStatus: Int,
        expected: BuildStatus,
    ) {
        val dto = fixture<BuildDto>().copy(isOnHold = isOnHold, status = buildStatus)

        val actual = subject(dto)

        assertEquals(expected, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val SUPPORTED_STATUS = 0..4

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = mutableListOf(
            // Format: on hold, status, expected
            arguments(false, 0, BuildStatus.RUNNING),
            arguments(false, 1, BuildStatus.SUCCESS),
            arguments(false, 2, BuildStatus.FAIL),
            arguments(false, 3, BuildStatus.ABORT),
            arguments(false, 4, BuildStatus.ABORT),
            arguments(false, fixture.range(5..Int.MAX_VALUE), BuildStatus.UNKNOWN),
        ).apply {
            addAll(SUPPORTED_STATUS.map { arguments(true, it, BuildStatus.PENDING) })
            add(arguments(true, fixture.range(5..Int.MAX_VALUE), BuildStatus.PENDING))
        }
    }
}
