package com.kevalpatel2106.feature.project.list.adapter

import com.kevalpatel2106.entity.Project

internal interface ProjectListAdapterCallback {
    fun onProjectSelected(project: Project)
    fun togglePin(project: Project, isChecked: Boolean)
}
