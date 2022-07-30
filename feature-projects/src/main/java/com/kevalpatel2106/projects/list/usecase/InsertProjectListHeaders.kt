package com.kevalpatel2106.projects.list.usecase

import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.projects.list.adapter.ProjectListItem

interface InsertProjectListHeaders {
    operator fun invoke(before: Project?, after: Project?): ProjectListItem?
}
