package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.CommitHash
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Commit(
    val hash: CommitHash,
    val message: String?,
    val author: String?,
    val commitAt: Date?,
    val commitViewUrl: Url?,
) : Parcelable
