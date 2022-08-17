@file:SuppressWarnings("MaxLineLength")

package com.kevalpatel2106.repositoryImpl.cache.db.projectTable

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface ProjectDao {
    @Query("SELECT COUNT(${ProjectTableInfo.REMOTE_ID}) FROM ${ProjectTableInfo.TABLE_NAME} WHERE ${ProjectTableInfo.REMOTE_ID} = :remoteId AND ${ProjectTableInfo.ACCOUNT_ID} = :accountId")
    suspend fun getCount(remoteId: String, accountId: Long): Int

    @Query("SELECT * FROM ${ProjectTableInfo.TABLE_NAME} WHERE ${ProjectTableInfo.REMOTE_ID} = :remoteId AND ${ProjectTableInfo.ACCOUNT_ID} = :accountId")
    suspend fun getProject(remoteId: String, accountId: Long): ProjectDto

    @Query("SELECT * FROM ${ProjectTableInfo.TABLE_NAME} WHERE ${ProjectTableInfo.REMOTE_ID} = :remoteId AND ${ProjectTableInfo.ACCOUNT_ID} = :accountId")
    suspend fun getProjectBasic(remoteId: String, accountId: Long): ProjectBasicDto

    @Query("SELECT * FROM ${ProjectTableInfo.TABLE_NAME} WHERE ${ProjectTableInfo.ACCOUNT_ID} = :accountId ORDER BY ${ProjectTableInfo.UPDATED_AT} DESC")
    fun getProjectsUpdatedDesc(accountId: Long): PagingSource<Int, ProjectDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUpdateProjects(projectDtos: List<ProjectDto>)

    @Query("DELETE FROM ${ProjectTableInfo.TABLE_NAME} WHERE ${ProjectTableInfo.ACCOUNT_ID} = :accountId")
    suspend fun deleteProjects(accountId: Long)
}
