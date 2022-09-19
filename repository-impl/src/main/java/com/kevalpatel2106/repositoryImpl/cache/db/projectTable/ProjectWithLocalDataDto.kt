package com.kevalpatel2106.repositoryImpl.cache.db.projectTable

import androidx.room.Embedded
import com.kevalpatel2106.repositoryImpl.cache.db.projectLocalDataTable.ProjectLocalDataDto

internal data class ProjectWithLocalDataDto(

    @Embedded
    val project: ProjectDto,

    @Embedded
    val localData: ProjectLocalDataDto?,
) {
    companion object {
        const val PROJECT = "project"
        const val LOCAL_DATA = "localData"
    }
}
