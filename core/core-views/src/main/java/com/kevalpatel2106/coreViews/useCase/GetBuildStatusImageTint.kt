package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.ColorRes
import com.kevalpatel2106.entity.BuildStatus

internal interface GetBuildStatusImageTint {

    @ColorRes
    operator fun invoke(buildStatus: BuildStatus): Int
}
