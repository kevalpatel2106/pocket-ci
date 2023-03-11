package com.kevalpatel2106.feature.project.list

import androidx.annotation.StringRes
import com.kevalpatel2106.entity.Project

internal sealed class ProjectListItem(val key: String) {
    data class ProjectItem(val project: Project) : ProjectListItem(project.toString())
    data class HeaderItem(@StringRes val title: Int) : ProjectListItem(title.toString())
}
