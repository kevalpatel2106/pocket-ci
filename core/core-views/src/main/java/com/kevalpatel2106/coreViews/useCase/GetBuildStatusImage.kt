package com.kevalpatel2106.coreViews.useCase

import androidx.annotation.DrawableRes
import com.kevalpatel2106.entity.BuildStatus

internal interface GetBuildStatusImage {

    @DrawableRes
    operator fun invoke(buildStatus: BuildStatus): Int
}
