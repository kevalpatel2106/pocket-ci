package com.kevalpatel2106.connector.bitrise.network.mapper

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.entity.BuildStatus
import javax.inject.Inject

internal class BuildStatusMapperImpl @Inject constructor() : BuildStatusMapper {

    override operator fun invoke(dto: BuildDto) = when {
        dto.isOnHold -> BuildStatus.PENDING
        dto.status == STATUS_NOT_FINISHED -> BuildStatus.RUNNING
        dto.status == STATUS_SUCCESSFUL -> BuildStatus.SUCCESS
        dto.status == STATUS_FAILED -> BuildStatus.FAIL
        dto.status == ABORTED_WITH_FAILURE -> BuildStatus.ABORT
        dto.status == ABORTED_WITH_SUCCESS -> BuildStatus.ABORT
        else -> BuildStatus.UNKNOWN
    }

    companion object {
        private const val STATUS_NOT_FINISHED = 0
        private const val STATUS_SUCCESSFUL = 1
        private const val STATUS_FAILED = 2
        private const val ABORTED_WITH_FAILURE = 3
        private const val ABORTED_WITH_SUCCESS = 4
    }
}
