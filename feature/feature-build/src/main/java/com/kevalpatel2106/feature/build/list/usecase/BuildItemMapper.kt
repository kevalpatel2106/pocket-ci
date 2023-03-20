package com.kevalpatel2106.feature.build.list.usecase

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.feature.build.list.model.BuildListItem.BuildItem
import java.util.Date

internal interface BuildItemMapper {
    operator fun invoke(build: Build, now: Date = Date()): BuildItem
}
