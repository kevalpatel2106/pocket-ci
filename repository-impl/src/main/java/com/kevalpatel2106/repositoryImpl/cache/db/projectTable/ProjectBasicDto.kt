package com.kevalpatel2106.repositoryImpl.cache.db.projectTable

import androidx.room.ColumnInfo
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId

internal data class ProjectBasicDto(
    @ColumnInfo(name = ProjectTableInfo.REMOTE_ID)
    val remoteId: ProjectId,

    @ColumnInfo(name = ProjectTableInfo.NAME)
    val name: String,

    @ColumnInfo(name = ProjectTableInfo.OWNER)
    val owner: String,

    @ColumnInfo(name = ProjectTableInfo.ACCOUNT_ID)
    val accountId: AccountId,
)
