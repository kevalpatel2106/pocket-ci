package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BuildDto(

    @Json(name = "build_number")
    val buildNumber: Int,

    @Json(name = "slug")
    val slug: String,

    @Json(name = "triggered_at")
    val triggeredAt: String,

    @Json(name = "started_on_worker_at")
    val startedOnWorkerAt: String? = null,

    @Json(name = "finished_at")
    val finishedAt: String? = null,

    @Json(name = "triggered_workflow")
    val triggeredWorkflow: String,

    @Json(name = "triggered_by")
    val triggeredBy: String?,

    @Json(name = "branch")
    val branch: String,

    @Json(name = "commit_hash")
    val commitHash: String,

    @Json(name = "commit_message")
    val commitMessage: String,

    @Json(name = "commit_view_url")
    val commitViewUrl: String? = null,

    @Json(name = "pull_request_id")
    val pullRequestId: Int? = null,

    @Json(name = "pull_request_view_url")
    val pullRequestViewUrl: String? = null,

    @Json(name = "pull_request_target_branch")
    val pullRequestTargetBranch: String? = null,

    @Json(name = "abort_reason")
    val abortReason: String? = null,

    @Json(name = "tag")
    val tag: String? = null,

    @Json(name = "is_processed")
    val isProcessed: Boolean,

    @Json(name = "is_on_hold")
    val isOnHold: Boolean,

    @Json(name = "status_text")
    val statusText: String,

    @Json(name = "status")
    val status: Int,
)
