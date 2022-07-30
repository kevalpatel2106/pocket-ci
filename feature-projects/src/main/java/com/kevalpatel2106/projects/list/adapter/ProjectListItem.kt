package com.kevalpatel2106.projects.list.adapter

import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.projects.list.adapter.ProjectListItemType.HEADER_ITEM
import com.kevalpatel2106.projects.list.adapter.ProjectListItemType.PROJECT_ITEM

sealed class ProjectListItem(val listItemType: ProjectListItemType, val compareId: String) {
    data class ProjectItem(val project: Project) :
        ProjectListItem(PROJECT_ITEM, project.remoteId.toString())

    data class HeaderItem(@StringRes val title: Int) :
        ProjectListItem(HEADER_ITEM, title.toString())
}
