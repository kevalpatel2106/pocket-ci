package com.kevalpatel2106.feature.build.list.usecase

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.feature.build.list.adapter.BuildListItem.BuildItem

internal interface BuildItemMapper {
    operator fun invoke(build: Build): BuildItem
}
