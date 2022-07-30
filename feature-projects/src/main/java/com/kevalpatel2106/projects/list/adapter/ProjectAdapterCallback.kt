package com.kevalpatel2106.projects.list.adapter

import com.kevalpatel2106.entity.Project

internal interface ProjectAdapterCallback {
    fun onProjectSelected(project: Project)
}
