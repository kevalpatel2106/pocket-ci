package com.kevalpatel2106.core.ui.useCase

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.BuildStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class GetBuildStatusImageTest {

    private val subject = GetBuildStatusImage()

    @ParameterizedTest(name = "given build status {0} when invoked then build image is {1}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check image`(
        buildStatus: BuildStatus,
        expectedIcon: ImageVector,
        @StringRes expectedContentDescription: Int,
        @ColorRes expectedTint: Int,
    ) {
        assertEquals(
            expectedIcon,
            subject(
                buildStatus,
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Gray,
            ).icon,
        )
    }

    @ParameterizedTest(name = "given build status {0} when invoked then content description is {2}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check content description`(
        buildStatus: BuildStatus,
        expectedIcon: ImageVector,
        @StringRes expectedContentDescription: Int,
        @ColorRes expectedTint: Int,
    ) {
        assertEquals(
            expectedContentDescription,
            subject(
                buildStatus,
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Gray,
            ).contentDescription,
        )
    }

    @ParameterizedTest(name = "given build status {0} when invoked then icon tint is {3}")
    @MethodSource("provideValues")
    fun `given build status when invoked then check icon tint`(
        buildStatus: BuildStatus,
        expectedIcon: ImageVector,
        @StringRes expectedContentDescription: Int,
        @ColorRes expectedTint: Int,
    ) {
        assertEquals(
            expectedTint,
            subject(
                buildStatus,
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Gray,
            ).tint,
        )
    }

    @Test
    fun `given build status running when invoked then exception thrown`() {
        assertThrows<IllegalStateException> {
            subject(
                BuildStatus.RUNNING,
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Gray,
            )
        }
    }

    @Test
    fun `given build status pending when invoked then exception thrown`() {
        assertThrows<IllegalStateException> {
            subject(
                BuildStatus.PENDING,
                Color.Red,
                Color.Yellow,
                Color.Green,
                Color.Gray,
            )
        }
    }

    companion object {
        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(
                BuildStatus.FAIL,
                Icons.Filled.Cancel,
                R.string.build_status_fail_content_description,
                Color.Red,
            ),
            arguments(
                BuildStatus.SUCCESS,
                Icons.Filled.CheckCircle,
                R.string.build_status_success_content_description,
                Color.Green,
            ),
            arguments(
                BuildStatus.ABORT,
                Icons.Filled.StopCircle,
                R.string.build_status_abort_content_description,
                Color.Yellow,
            ),
            arguments(
                BuildStatus.SKIPPED,
                Icons.Filled.SkipNext,
                R.string.build_status_skipped_content_description,
                Color.Gray,
            ),
            arguments(
                BuildStatus.UNKNOWN,
                Icons.Filled.QuestionMark,
                R.string.build_status_unknown_content_description,
                Color.Red,
            ),
        )
    }
}
