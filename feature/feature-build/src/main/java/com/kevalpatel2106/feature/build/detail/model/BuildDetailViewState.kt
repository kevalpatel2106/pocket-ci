package com.kevalpatel2106.feature.build.detail.model

import com.kevalpatel2106.entity.BuildStatus

data class BuildDetailViewState(
    val numberText: String,
    val buildStatusText: String,
    val buildStatus: BuildStatus,
    val workflowName: String,
    val headBranch: String,
    val commitMessage: String?,
    val abortMessage: String?,
    val commitHash: String?,
    val triggeredTime: String,
    val executionTime: String,
    val showCommitFullViewButton: Boolean,
    val showAbortFullViewButton: Boolean,
    val hideActorInfo: Boolean,
    val showCommitInfo: Boolean,
    val showAbortInfo: Boolean,
    val showBuildLogButton: Boolean,
    val showJobsListButton: Boolean,
    val showArtifactsButton: Boolean,
)
