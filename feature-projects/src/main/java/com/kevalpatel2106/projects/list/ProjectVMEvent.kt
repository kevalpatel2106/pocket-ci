package com.kevalpatel2106.projects.list

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId

internal sealed class ProjectVMEvent {
    data class OpenBuildsList(val accountId: AccountId, val projectId: ProjectId) : ProjectVMEvent()
    object RefreshProjects : ProjectVMEvent()
    object ShowErrorView : ProjectVMEvent()
    object RetryLoading : ProjectVMEvent()
}
