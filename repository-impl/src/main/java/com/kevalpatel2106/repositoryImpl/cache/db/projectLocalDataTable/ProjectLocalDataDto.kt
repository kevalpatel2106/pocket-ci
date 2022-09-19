package com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.NO_ACTION
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectDto
import com.kevalpatel2106.repositoryImpl.cache.db.projectTable.ProjectTableInfo
import java.util.Date

@Entity(
    tableName = ProjectLocalDataTableInfo.TABLE_NAME,
    primaryKeys = [
        ProjectLocalDataTableInfo.ACCOUNT_ID,
        ProjectLocalDataTableInfo.REMOTE_ID,
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProjectDto::class,
            parentColumns = arrayOf(
                ProjectTableInfo.REMOTE_ID,
                ProjectTableInfo.ACCOUNT_ID,
            ),
            childColumns = arrayOf(
                ProjectLocalDataTableInfo.REMOTE_ID,
                ProjectLocalDataTableInfo.ACCOUNT_ID,
            ),
            onDelete = NO_ACTION, // This data needs to be persistent when project cache updates
        ),
    ],
)
internal data class ProjectLocalDataDto(
    @ColumnInfo(name = ProjectLocalDataTableInfo.REMOTE_ID)
    val remoteId: ProjectId,

    @ColumnInfo(name = ProjectLocalDataTableInfo.ACCOUNT_ID)
    val accountId: AccountId,

    @ColumnInfo(name = ProjectLocalDataTableInfo.IS_PINNED, defaultValue = "0")
    val isPinned: Boolean,

    @ColumnInfo(name = ProjectLocalDataTableInfo.CREATED_AT, defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Date = Date(),
)
