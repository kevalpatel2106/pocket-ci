package com.kevalpatel2106.entity

enum class BuildStatus {
    PENDING,
    RUNNING,
    FAIL,
    SUCCESS,
    ABORT,
    SKIPPED,
    UNKNOWN
}

private val IN_PROGRESS_BUILD_STATUS = listOf(BuildStatus.RUNNING, BuildStatus.PENDING)
fun BuildStatus.isInProgress() = this in IN_PROGRESS_BUILD_STATUS
