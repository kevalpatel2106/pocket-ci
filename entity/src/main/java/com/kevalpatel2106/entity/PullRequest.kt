package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.entity.id.PullRequestId
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class PullRequest(
    val id: PullRequestId,
    val number: Long?,
    val headBranch: String,
    val baseBranch: String
) : Parcelable
