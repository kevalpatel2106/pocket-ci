package com.kevalpatel2106.feature.project.list

import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId

internal sealed class ProjectListVMEvent {
    data class ShowErrorLoadingProjects(val error: DisplayError) : ProjectListVMEvent()
    data class OpenBuildsList(val accountId: AccountId, val projectId: ProjectId) :
        ProjectListVMEvent()
    object Close : ProjectListVMEvent()
}
