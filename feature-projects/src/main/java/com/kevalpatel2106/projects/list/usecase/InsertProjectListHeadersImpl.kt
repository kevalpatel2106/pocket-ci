package com.kevalpatel2106.projects.list.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.projects.R
import com.kevalpatel2106.projects.list.adapter.ProjectListItem
import com.kevalpatel2106.projects.list.adapter.ProjectListItem.HeaderItem
import javax.inject.Inject

class InsertProjectListHeadersImpl @Inject constructor() : InsertProjectListHeaders {

    override operator fun invoke(
        before: Project?,
        after: Project?,
    ): ProjectListItem? {
        val beforeIsFav = before?.isFavourite == true
        val afterIsFav = after?.isFavourite == true
        return when {
            beforeIsFav && after == null -> null
            !beforeIsFav && afterIsFav -> HeaderItem(R.string.list_header_favourites)
            beforeIsFav && !afterIsFav -> HeaderItem(R.string.list_header_other)
            else -> null
        }
    }
}
