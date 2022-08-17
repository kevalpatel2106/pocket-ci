package com.kevalpatel2106.repositoryImpl.cache.db.projectTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId
import java.util.Date

@Entity(
    tableName = ProjectTableInfo.TABLE_NAME,
    primaryKeys = [
        ProjectTableInfo.ACCOUNT_ID,
        ProjectTableInfo.REMOTE_ID,
    ],
    indices = [
        Index(value = [ProjectTableInfo.REMOTE_ID, ProjectTableInfo.ACCOUNT_ID], unique = true),
    ],
)
internal data class ProjectDto(
    @ColumnInfo(name = ProjectTableInfo.REMOTE_ID)
    val remoteId: ProjectId,

    @ColumnInfo(name = ProjectTableInfo.NAME)
    val name: String,

    @ColumnInfo(name = ProjectTableInfo.OWNER)
    val owner: String,

    @ColumnInfo(name = ProjectTableInfo.ACCOUNT_ID)
    val accountId: AccountId,

    @ColumnInfo(name = ProjectTableInfo.REPO_URL)
    val repoUrl: Url?,

    @ColumnInfo(name = ProjectTableInfo.IMAGE)
    val image: Url?,

    @ColumnInfo(name = ProjectTableInfo.IS_DISABLED)
    val isDisabled: Boolean,

    @ColumnInfo(name = ProjectTableInfo.IS_PUBLIC)
    val isPublic: Boolean,

    @ColumnInfo(name = ProjectTableInfo.UPDATED_AT)
    val lastUpdatedAt: Date,

    @ColumnInfo(name = ProjectTableInfo.SAVED_AT)
    val savedAt: Date,
)
