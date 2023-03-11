package com.kevalpatel2106.feature.project.list.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.feature.project.list.ProjectListItem

internal interface InsertProjectListHeaders {
    operator fun invoke(before: Project?, after: Project?): ProjectListItem?
}
