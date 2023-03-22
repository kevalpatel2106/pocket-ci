package com.kevalpatel2106.cache.db.projectTable

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.kevalpatel2106.cache.db.projectTable.ProjectWithLocalDataDto.Companion.LOCAL_DATA
import com.kevalpatel2106.cache.db.projectTable.ProjectWithLocalDataDto.Companion.PROJECT
import com.kevalpatel2106.cache.db.projectLocalDataTable.ProjectLocalDataTableInfo as PLT
import com.kevalpatel2106.cache.db.projectTable.ProjectTableInfo as PT

@Dao
interface ProjectDao {
    @Query(
        "SELECT COUNT(${PT.REMOTE_ID}) FROM ${PT.TABLE_NAME} " +
            "WHERE ${PT.REMOTE_ID} = :remoteId AND ${PT.ACCOUNT_ID} = :accountId",
    )
    suspend fun getCount(remoteId: String, accountId: Long): Int

    @Query(
        "SELECT * FROM ${PT.TABLE_NAME} as $PROJECT " +
            "LEFT JOIN ${PLT.TABLE_NAME} as $LOCAL_DATA " +
            "ON $LOCAL_DATA.${PLT.REMOTE_ID} = $PROJECT.${PT.REMOTE_ID} " +
            "AND $LOCAL_DATA.${PLT.ACCOUNT_ID} = $PROJECT.${PT.ACCOUNT_ID} " +
            "WHERE $PROJECT.${PT.REMOTE_ID} = :remoteId AND $PROJECT.${PT.ACCOUNT_ID} = :accountId",
    )
    suspend fun getProject(remoteId: String, accountId: Long): ProjectWithLocalDataDto

    @Query(
        "SELECT * FROM ${PT.TABLE_NAME} " +
            "WHERE ${PT.REMOTE_ID} = :remoteId AND ${PT.ACCOUNT_ID} = :accountId",
    )
    @RewriteQueriesToDropUnusedColumns
    suspend fun getProjectBasic(remoteId: String, accountId: Long): ProjectBasicDto

    @Query(
        "SELECT * FROM ${PT.TABLE_NAME} as $PROJECT " +
            "LEFT JOIN (SELECT * FROM ${PLT.TABLE_NAME} WHERE ${PLT.IS_PINNED} = 1) as $LOCAL_DATA " +
            "ON $LOCAL_DATA.${PLT.REMOTE_ID} = $PROJECT.${PT.REMOTE_ID} " +
            "AND $LOCAL_DATA.${PLT.ACCOUNT_ID} = $PROJECT.${PT.ACCOUNT_ID} " +
            "WHERE $PROJECT.${PT.ACCOUNT_ID} = :accountId " +
            "ORDER BY $LOCAL_DATA.${PLT.IS_PINNED} DESC, $PROJECT.${PT.UPDATED_AT} DESC",
    )
    fun getProjectsUpdatedDesc(accountId: Long): PagingSource<Int, ProjectWithLocalDataDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateProjects(projectDtos: List<ProjectDto>)

    @Query("DELETE FROM ${PT.TABLE_NAME} WHERE ${PT.ACCOUNT_ID} = :accountId")
    suspend fun deleteProjects(accountId: Long)

    @VisibleForTesting
    @Query("SELECT COUNT(${PT.REMOTE_ID}) FROM ${PT.TABLE_NAME}")
    suspend fun getTotalProjects(): Int

    @Transaction
    suspend fun replaceWithNewProjects(accountId: Long, projectDtos: List<ProjectDto>) {
        deleteProjects(accountId)
        addUpdateProjects(projectDtos)
    }
}
