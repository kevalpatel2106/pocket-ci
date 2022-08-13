package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Build(
    val id: BuildId,
    val projectId: ProjectId,
    val number: Long,

    val triggeredAt: Date,
    val finishedAt: Date?,
    val triggeredBy: String?,

    val status: BuildStatus,
    val headBranch: String,

    val workflow: Workflow,
    val pullRequest: PullRequest?,
    val commit: Commit?,
    val abortReason: String?,
) : Parcelable
