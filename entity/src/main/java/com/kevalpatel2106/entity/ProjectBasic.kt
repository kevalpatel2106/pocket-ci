package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectBasic(
    val remoteId: ProjectId,
    val accountId: AccountId,
    val name: String,
    val owner: String,
) : Parcelable
