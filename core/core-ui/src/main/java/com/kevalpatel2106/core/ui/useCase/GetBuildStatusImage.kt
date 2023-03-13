package com.kevalpatel2106.core.ui.useCase

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.entity.BuildStatus

internal class GetBuildStatusImage {

    operator fun invoke(buildStatus: BuildStatus) = when (buildStatus) {
        BuildStatus.PENDING -> error("Pending build status doesn't show image.")
        BuildStatus.RUNNING -> error("Running build status doesn't show image.")
        BuildStatus.FAIL -> BuildStatusImageData(
            icon = Icons.Filled.Cancel,
            contentDescription = R.string.build_status_fail_content_description,
            tint = R.color.red,
        )
        BuildStatus.SUCCESS -> BuildStatusImageData(
            icon = Icons.Filled.CheckCircle,
            contentDescription = R.string.build_status_success_content_description,
            tint = R.color.green,
        )
        BuildStatus.ABORT -> BuildStatusImageData(
            icon = Icons.Filled.StopCircle,
            contentDescription = R.string.build_status_abort_content_description,
            tint = R.color.yellow,
        )
        BuildStatus.SKIPPED -> BuildStatusImageData(
            icon = Icons.Filled.SkipNext,
            contentDescription = R.string.build_status_skipped_content_description,
            tint = R.color.gray5,
        )
        BuildStatus.UNKNOWN -> BuildStatusImageData(
            icon = Icons.Filled.QuestionMark,
            contentDescription = R.string.build_status_unknown_content_description,
            tint = R.color.red,
        )
    }

    data class BuildStatusImageData(
        val icon: ImageVector,
        @StringRes val contentDescription: Int,
        @ColorRes val tint: Int,
    )
}