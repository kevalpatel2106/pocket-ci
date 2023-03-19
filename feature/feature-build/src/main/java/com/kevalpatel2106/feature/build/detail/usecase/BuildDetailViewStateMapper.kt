package com.kevalpatel2106.feature.build.detail.usecase

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.feature.build.detail.model.BuildDetailViewState
import java.util.Date

internal interface BuildDetailViewStateMapper {
    operator fun invoke(
        build: Build,
        ciInfo: CIInfo?,
        now: Date = Date()
    ): BuildDetailViewState
}