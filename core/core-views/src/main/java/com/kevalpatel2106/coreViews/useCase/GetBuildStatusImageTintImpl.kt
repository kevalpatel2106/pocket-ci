package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.ColorRes
import com.kevalpatel2106.coreViews.R
import com.kevalpatel2106.entity.BuildStatus
import javax.inject.Inject

internal class GetBuildStatusImageTintImpl @Inject constructor() : GetBuildStatusImageTint {

    @ColorRes
    override operator fun invoke(buildStatus: BuildStatus) = when (buildStatus) {
        BuildStatus.PENDING -> error("Pending build status doesn't show image.")
        BuildStatus.RUNNING -> error("Running build status doesn't show image.")
        BuildStatus.FAIL -> R.color.red
        BuildStatus.SUCCESS -> R.color.green
        BuildStatus.ABORT -> R.color.yellow
        BuildStatus.SKIPPED -> R.color.orange
        BuildStatus.UNKNOWN -> R.color.gray5
    }
}
