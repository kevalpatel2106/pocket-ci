package com.kevalpatel2106.feature.build.detail.usecase

import com.kevalpatel2106.core.usecase.TimeDifferenceFormatter
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.isInProgress
import com.kevalpatel2106.feature.build.detail.model.BuildDetailViewState
import java.util.Date
import javax.inject.Inject

internal class BuildDetailViewStateMapperImpl @Inject constructor(
    private val timeDifferenceFormatter: TimeDifferenceFormatter,
) : BuildDetailViewStateMapper {

    override fun invoke(build: Build, ciInfo: CIInfo?, now: Date): BuildDetailViewState =
        with(build) {
            BuildDetailViewState(
                numberText = "#${number}",
                buildStatus = status,
                buildStatusText = status.name.lowercase(),
                workflowName = build.workflow.name,
                headBranch = build.headBranch,
                commitHash = commit?.hash?.shortHash,
                commitMessage = commit?.message,
                abortMessage = abortReason,
                triggeredTime = timeDifferenceFormatter(
                    dateStart = triggeredAt,
                    dateEnd = now,
                    appendText = "ago", // TODO convert to use string resource
                    showMorePrecise = false,
                ),
                executionTime = timeDifferenceFormatter(
                    dateStart = triggeredAt,
                    dateEnd = finishedAt ?: now,
                    showMorePrecise = true,
                ),
                hideActorInfo = triggeredBy.isNullOrBlank(),
                showCommitInfo = commit?.message.isNullOrBlank().not(),
                showAbortInfo = !abortReason.isNullOrBlank(),
                showCommitFullViewButton = commit?.message.isLongText(),
                showAbortFullViewButton = abortReason.isLongText(),
                showBuildLogButton = ciInfo?.supportBuildLevelLogs ?: false,
                showJobsListButton = ciInfo?.supportJobs ?: false,
                showArtifactsButton = shouldShowArtifacts(build, ciInfo),
            )
        }

    private fun shouldShowArtifacts(
        build: Build,
        ciInfo: CIInfo?
    ) = !build.status.isInProgress() && ciInfo?.supportViewArtifacts ?: false

    private fun String?.isLongText() = orEmpty().lines().size > LONG_TEXT_LINE_THRESHOLD

    companion object {
        private const val LONG_TEXT_LINE_THRESHOLD = 6
    }
}