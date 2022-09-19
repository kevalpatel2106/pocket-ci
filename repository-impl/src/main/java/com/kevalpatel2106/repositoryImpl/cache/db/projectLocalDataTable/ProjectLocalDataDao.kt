package com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
internal interface ProjectLocalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProjectPersistentData(dto: ProjectLocalDataDto)
}
