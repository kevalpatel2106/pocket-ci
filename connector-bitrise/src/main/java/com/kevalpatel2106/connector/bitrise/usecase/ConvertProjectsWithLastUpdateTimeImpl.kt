package com.kevalpatel2106.connector.bitrise.usecase

import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapper
import com.kevalpatel2106.entity.Project
import com.kevalpatel2106.entity.id.AccountId
import java.util.Date
import javax.inject.Inject

internal class ConvertProjectsWithLastUpdateTimeImpl @Inject constructor(
    private val projectMapper: ProjectMapper,
) :
    ConvertProjectsWithLastUpdateTime {

    /**
     * Bitrise doesn't give us last updated time in API response. However because of sort
     * order in the API, we know that the apps are returned in last updated order.
     * Subtracting 1ms from lastUpdatedAt of previous item while converting to entity will make
     * sure that in each entity lastUpdatedAt in two item is never same and is always less then
     * previous item making sure the order in the project db stays the same as returned from api.
     */
    override suspend operator fun invoke(
        accountId: AccountId,
        dtos: List<ProjectDto>,
    ): List<Project> {
        var epochMills = System.currentTimeMillis()
        return dtos.map {
            epochMills -= 1
            projectMapper(it, accountId, lastUpdatedAt = Date(epochMills))
        }
    }
}
