package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BuildDto(

    @Json(name = "name")
    val name: String,

    @Json(name = "head_commit")
    val headCommit: CommitDto,

    @Json(name = "head_branch")
    val headBranch: String,

    @Json(name = "run_started_at")
    val runStartedAt: String? = null,

    @Json(name = "repository")
    val repository: ProjectDto? = null,

    @Json(name = "conclusion")
    val conclusion: String?,

    @Json(name = "id")
    val id: Long,

    @Json(name = "head_repository")
    val headRepository: ProjectDto? = null,

    @Json(name = "triggering_actor")
    val triggeringActor: BaseUserDto,

    @Json(name = "actor")
    val actor: BaseUserDto,

    @Json(name = "run_number")
    val runNumber: Int,

    @Json(name = "status")
    val status: String,
)
