package com.kevalpatel2106.cache.db.projectTable

import androidx.room.Embedded
import com.kevalpatel2106.cache.db.projectLocalDataTable.ProjectLocalDataDto

data class ProjectWithLocalDataDto(

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
