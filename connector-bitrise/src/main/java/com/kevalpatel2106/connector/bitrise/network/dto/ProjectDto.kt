package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Sample response:
 * {
 *      "slug": "eeeeefffff00000",
 *      "title": "sample-app",
 *      "project_type": "android",
 *      "provider": "github",
 *      "repo_owner": "example-user",
 *      "repo_url": "git@github.com:example-user/sample-app.git",
 *      "repo_slug": "android-gradle-kotlin-dsl",
 *      "is_disabled": false,
 *      "status": -1,
 *      "is_public": false,
 *      "owner": {
 *          "account_type": "organization",
 *          "name": "Test Org",
 *          "slug": "fffffeeeee00000"
 *      },
 *      "avatar_url": null
 * }
 */
@JsonClass(generateAdapter = true)
internal data class ProjectDto(

    @Json(name = "project_type")
    val projectType: String? = null,

    @Json(name = "repo_url")
    val repoUrl: String? = null,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "provider")
    val provider: String? = null,

    @Json(name = "is_disabled")
    val isDisabled: Boolean,

    @Json(name = "is_public")
    val isPublic: Boolean,

    @Json(name = "title")
    val title: String,

    @Json(name = "repo_slug")
    val repoSlug: String? = null,

    @Json(name = "repo_owner")
    val repoOwner: String,

    @Json(name = "slug")
    val slug: String,

    @Json(name = "status")
    val status: Int,
)
