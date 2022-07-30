package com.kevalpatel2106.connector.github.network.mapper

import com.kevalpatel2106.connector.github.network.dto.BuildDto
import com.kevalpatel2106.entity.BuildStatus
import timber.log.Timber
import javax.inject.Inject

internal class BuildStatusMapperImpl @Inject constructor() : BuildStatusMapper {

    override operator fun invoke(dto: BuildDto) = when (dto.status) {
        STATUS_COMPLETED -> handleCompleted(dto.conclusion)
        STATUS_QUEUED -> BuildStatus.PENDING
        STATUS_IN_PROGRESS -> BuildStatus.RUNNING
        else -> {
            Timber.w("Unknown build status: ${dto.status}")
            BuildStatus.UNKNOWN
        }
    }

    private fun handleCompleted(conclusion: String?) = when (conclusion) {
        CONCLUSION_SUCCESS -> BuildStatus.SUCCESS
        CONCLUSION_FAILURE -> BuildStatus.FAIL
        CONCLUSION_CANCELED -> BuildStatus.ABORT
        CONCLUSION_NOT_READY -> BuildStatus.UNKNOWN
        else -> {
            Timber.w("Unknown build conclusion for status $STATUS_COMPLETED: $conclusion")
            BuildStatus.UNKNOWN
        }
    }

    companion object {
        private val CONCLUSION_NOT_READY = null
        private const val CONCLUSION_SUCCESS = "success"
        private const val CONCLUSION_FAILURE = "failure"
        private const val CONCLUSION_CANCELED = "cancelled"
        private const val STATUS_COMPLETED = "completed"
        private const val STATUS_QUEUED = "queued"
        private const val STATUS_IN_PROGRESS = "in_progress"
    }
}
