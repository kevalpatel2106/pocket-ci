package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.DrawableRes
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.entity.BuildStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class GetBuildStatusImageImplTest {

    private val subject = GetBuildStatusImageImpl()

    @ParameterizedTest(name = "given build status {0} when invoked then build image is {1}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check image`(
        buildStatus: BuildStatus,
        @DrawableRes expectedImage: Int,
    ) {
        assertEquals(expectedImage, subject(buildStatus))
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
            arguments(BuildStatus.FAIL, R.drawable.ic_fail_filled),
            arguments(BuildStatus.SUCCESS, R.drawable.ic_check_filled),
            arguments(BuildStatus.ABORT, R.drawable.ic_stop_filled),
            arguments(BuildStatus.SKIPPED, R.drawable.ic_dot_filled),
            arguments(BuildStatus.UNKNOWN, R.drawable.ic_circle_filled),
        )
    }
}
