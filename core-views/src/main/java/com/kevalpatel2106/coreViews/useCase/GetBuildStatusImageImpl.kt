package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.DrawableRes
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.entity.BuildStatus
import javax.inject.Inject

internal class GetBuildStatusImageImpl @Inject constructor() : GetBuildStatusImage {

    @DrawableRes
    override operator fun invoke(buildStatus: BuildStatus) = when (buildStatus) {
        BuildStatus.PENDING -> error("Pending build status doesn't show image.")
        BuildStatus.RUNNING -> error("Running build status doesn't show image.")
        BuildStatus.FAIL -> R.drawable.ic_fail_filled
        BuildStatus.SUCCESS -> R.drawable.ic_check_filled
        BuildStatus.ABORT -> R.drawable.ic_stop_filled
        BuildStatus.SKIPPED -> R.drawable.ic_dot_filled
        BuildStatus.UNKNOWN -> R.drawable.ic_circle_filled
    }
}
