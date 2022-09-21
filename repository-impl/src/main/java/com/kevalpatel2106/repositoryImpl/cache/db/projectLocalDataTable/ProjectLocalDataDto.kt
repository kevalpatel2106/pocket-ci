package com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import java.util.Date

@Entity(
    tableName = ProjectLocalDataTableInfo.TABLE_NAME,
    primaryKeys = [
        ProjectLocalDataTableInfo.ACCOUNT_ID,
        ProjectLocalDataTableInfo.REMOTE_ID,
    ],
)
internal data class ProjectLocalDataDto(
    @ColumnInfo(name = ProjectLocalDataTableInfo.REMOTE_ID)
    val remoteId: ProjectId,

    @ColumnInfo(name = ProjectLocalDataTableInfo.ACCOUNT_ID)
    val accountId: AccountId,

    @ColumnInfo(name = ProjectLocalDataTableInfo.IS_PINNED, defaultValue = "0")
    val isPinned: Boolean,

    @ColumnInfo(name = ProjectLocalDataTableInfo.UPDATED_AT, defaultValue = "CURRENT_TIMESTAMP")
    val updatedAt: Date = Date(),
)
