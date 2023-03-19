package com.kevalpatel2106.core.ui.useCase

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

internal class GetBuildStatusImage {

    operator fun invoke(
        buildStatus: BuildStatus,
        red: Color,
        yellow: Color,
        green: Color,
        gray: Color,
    ) = when (buildStatus) {
        BuildStatus.PENDING -> error("Pending build status doesn't show image.")
        BuildStatus.RUNNING -> error("Running build status doesn't show image.")
        BuildStatus.FAIL -> BuildStatusImageData(
            icon = Icons.Filled.Cancel,
            contentDescription = R.string.build_status_fail_content_description,
            tint = red,
        )
        BuildStatus.SUCCESS -> BuildStatusImageData(
            icon = Icons.Filled.CheckCircle,
            contentDescription = R.string.build_status_success_content_description,
            tint = green,
        )
        BuildStatus.ABORT -> BuildStatusImageData(
            icon = Icons.Filled.StopCircle,
            contentDescription = R.string.build_status_abort_content_description,
            tint = yellow,
        )
        BuildStatus.SKIPPED -> BuildStatusImageData(
            icon = Icons.Filled.SkipNext,
            contentDescription = R.string.build_status_skipped_content_description,
            tint = gray,
        )
        BuildStatus.UNKNOWN -> BuildStatusImageData(
            icon = Icons.Filled.QuestionMark,
            contentDescription = R.string.build_status_unknown_content_description,
            tint = red,
        )
    }

    data class BuildStatusImageData(
        val icon: ImageVector,
        @StringRes val contentDescription: Int,
        val tint: Color,
    )
}