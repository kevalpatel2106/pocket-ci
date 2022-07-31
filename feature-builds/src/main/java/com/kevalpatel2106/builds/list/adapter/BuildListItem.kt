package com.kevalpatel2106.builds.list.adapter

import com.kevalpatel2106.builds.list.adapter.BuildListItemType.BUILD_ITEM
import com.kevalpatel2106.entity.Build

sealed class BuildListItem(val listItemType: BuildListItemType, val compareId: String) {

    data class BuildItem(
        val build: Build,
        val numberText: String
    ) : BuildListItem(BUILD_ITEM, build.id.toString())
}
