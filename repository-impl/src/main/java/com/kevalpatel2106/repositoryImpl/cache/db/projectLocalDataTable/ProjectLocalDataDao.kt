package com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface ProjectLocalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLocalData(dto: ProjectLocalDataDto)

    @Query("SELECT * FROM ${ProjectLocalDataTableInfo.TABLE_NAME}")
    suspend fun getAll(): List<ProjectLocalDataDto>
}
