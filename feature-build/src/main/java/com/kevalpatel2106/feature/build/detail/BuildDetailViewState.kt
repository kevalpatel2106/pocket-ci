package com.kevalpatel2106.feature.build.detail

import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.isInProgress

data class BuildDetailViewState(
    val hideBuildLogButton: Boolean,
    val hideJobsListButton: Boolean,
    val hideArtifactsButton: Boolean,
    val build: Build,
) {
    val hideActorInfo = build.triggeredBy.isNullOrBlank()
    val hideCommitInfo = build.commit?.message.isNullOrBlank()
    val hideAbortInfo = build.abortReason.isNullOrBlank()
    val hideCommitFullViewButton =
        build.commit?.message.orEmpty().lines().size < LONG_TEXT_LINE_THRESHOLD
    val hideAbortFullViewButton =
        build.abortReason.orEmpty().lines().size < LONG_TEXT_LINE_THRESHOLD
    val numberText = "#${build.number}"
    val status = build.status.name.lowercase()
    val triggeredTimeDifference = TimeDifferenceData(
        dateOfEventStart = build.triggeredAt,
        dateOfEventEnd = null,
    )
    val executionTimeDifference = TimeDifferenceData(
        dateOfEventStart = build.triggeredAt,
        dateOfEventEnd = build.finishedAt,
    )

    companion object {
        private const val LONG_TEXT_LINE_THRESHOLD = 6

        fun initialState(build: Build) = BuildDetailViewState(
            hideBuildLogButton = true,
            hideJobsListButton = true,
            hideArtifactsButton = build.status.isInProgress(),
            build = build,
        )
    }
}
