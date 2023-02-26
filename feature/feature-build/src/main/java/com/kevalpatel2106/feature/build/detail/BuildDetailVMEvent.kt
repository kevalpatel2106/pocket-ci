package com.kevalpatel2106.feature.build.detail

import androidx.annotation.StringRes
import com.kevalpatel2106.entity.DisplayError
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId

internal sealed class BuildDetailVMEvent {
    data class ShowErrorAndClose(val error: DisplayError) : BuildDetailVMEvent()

    class OpenBuildLogs(
        val accountId: AccountId,
        val projectId: ProjectId,
        val buildId: BuildId,
    ) : BuildDetailVMEvent()

    class OpenBuildArtifacts(
        val accountId: AccountId,
        val projectId: ProjectId,
        val buildId: BuildId,
        val title: String,
    ) : BuildDetailVMEvent()

    class OpenJobs(
        val accountId: AccountId,
        val projectId: ProjectId,
        val buildId: BuildId,
        val title: String,
    ) : BuildDetailVMEvent()

    class OpenMarkDownViewer(
        @StringRes val titleRes: Int,
        val commitMessage: String,
    ) : BuildDetailVMEvent()
}
