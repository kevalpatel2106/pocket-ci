package com.kevalpatel2106.feature.build.list.model

import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.entity.BuildStatus
import com.kevalpatel2106.entity.id.BuildId

internal sealed class BuildListItem(val key: String) {

    data class BuildItem(
        val buildId: BuildId,
        val buildStatus: BuildStatus,
        val workflowName: String,
        val numberText: String,
        val headBranch: String,
        val commitMessage: String?,
        val commitHash: String?,
        val triggeredBy: String?,
        val triggeredTime: String,
        val executionTime: String
    ) : BuildListItem(buildId.toString()) {
        lateinit var build: Build
    }
}
