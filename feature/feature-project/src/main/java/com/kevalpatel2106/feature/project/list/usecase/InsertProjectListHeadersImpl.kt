package com.kevalpatel2106.feature.project.list.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.project.list.ProjectListItem
import com.kevalpatel2106.feature.project.list.ProjectListItem.HeaderItem
import javax.inject.Inject

internal class InsertProjectListHeadersImpl @Inject constructor() : InsertProjectListHeaders {

    override operator fun invoke(
        before: Project?,
        after: Project?,
    ): ProjectListItem? {
        val beforeIsFav = before?.isPinned == true
        val afterIsFav = after?.isPinned == true
        return when {
            beforeIsFav && after == null -> null
            !beforeIsFav && afterIsFav -> HeaderItem(R.string.list_header_pinned)
            beforeIsFav && !afterIsFav -> HeaderItem(R.string.list_header_other)
            else -> null
        }
    }
}
