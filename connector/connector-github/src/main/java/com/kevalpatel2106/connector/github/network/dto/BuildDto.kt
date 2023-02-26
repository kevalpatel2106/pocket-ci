package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BuildDto(

    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "event")
    val event: String,

    @Json(name = "head_commit")
    val headCommit: CommitDto?,

    @Json(name = "head_branch")
    val headBranch: String,

    @Json(name = "pull_requests")
    val pullRequests: List<PullRequestDto>,

    @Json(name = "head_repository")
    val headRepository: ProjectDto? = null,

    @Json(name = "run_started_at")
    val runStartedAt: String? = null,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "repository")
    val repository: ProjectDto? = null,

    @Json(name = "conclusion")
    val conclusion: String?,

    @Json(name = "triggering_actor")
    val triggeringActor: BaseUserDto?,

    @Json(name = "actor")
    val actor: BaseUserDto,

    @Json(name = "run_number")
    val runNumber: Long,

    @Json(name = "status")
    val status: String,
)
