package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.ColorRes
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.entity.BuildStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class GetBuildStatusImageTintImplTest {

    private val subject = GetBuildStatusImageTintImpl()

    @ParameterizedTest(name = "given build status {0} when invoked then check image tint {1}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check image tint`(
        buildStatus: BuildStatus,
        @ColorRes expectedTintColor: Int,
    ) {
        assertEquals(expectedTintColor, subject(buildStatus))
    }

    @Test
    fun `given build status running when invoked then exception thrown`() {
        assertThrows<IllegalStateException> { subject(BuildStatus.RUNNING) }
    }

    @Test
    fun `given build status pending when invoked then exception thrown`() {
        assertThrows<IllegalStateException> { subject(BuildStatus.PENDING) }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(BuildStatus.FAIL, R.color.red),
            arguments(BuildStatus.SUCCESS, R.color.green),
            arguments(BuildStatus.ABORT, R.color.yellow),
            arguments(BuildStatus.SKIPPED, R.color.orange),
            arguments(BuildStatus.UNKNOWN, R.color.gray5),
        )
    }
}
