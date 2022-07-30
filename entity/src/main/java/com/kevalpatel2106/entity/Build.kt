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
    val number: Int,

    val triggeredAt: Date,
    val finishedAt: Date?,
    val triggeredBy: String?,

    val workflow: String,
    val status: BuildStatus,

    val headBranch: String,

    val commitHash: String,
    val commitAt: Date?,
    val commitAuthor: String?,
    val commitMessage: String?,
    val commitViewUrl: Url?,
) : Parcelable
