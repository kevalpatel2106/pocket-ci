package com.kevalpatel2106.feature.project.list.eventHandler

import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent

internal interface ProjectListVMEventHandler {

    operator fun invoke(event: ProjectListVMEvent)
}