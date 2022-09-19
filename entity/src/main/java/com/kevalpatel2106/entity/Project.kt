package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Project(
    val remoteId: ProjectId,
    val accountId: AccountId,
    val name: String,
    val owner: String,
    val image: Url?,
    val repoUrl: Url?,
    val isDisabled: Boolean,
    val isPublic: Boolean,
    val isPinned: Boolean,
    val lastUpdatedAt: Date,
) : Parcelable {
    @IgnoredOnParcel
    val fullName = "$owner/$name"
}
