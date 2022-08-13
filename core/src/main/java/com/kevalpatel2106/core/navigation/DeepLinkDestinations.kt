package com.kevalpatel2106.core.navigation

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId

sealed class DeepLinkDestinations(val value: String, val navArgs: Map<String, String?>) {
    object Settings : DeepLinkDestinations("settings", emptyMap())
    object CiSelection : DeepLinkDestinations("ci-selection", emptyMap())
    object AccountsList : DeepLinkDestinations("accounts-list", emptyMap())

    data class ProjectsList(private val accountId: AccountId) : DeepLinkDestinations(
        value = "projects",
        navArgs = mapOf("accountId" to accountId.getValue().toString()),
    )

    data class BuildsList(
        private val accountId: AccountId,
        private val projectId: ProjectId,
    ) : DeepLinkDestinations(
        value = "builds-list",
        navArgs = mapOf(
            "accountId" to accountId.getValue().toString(),
            "projectId" to projectId.getValue(),
        ),
    )

    data class BuildLog(
        private val accountId: AccountId,
        private val projectId: ProjectId,
        private val buildId: BuildId,
        private val jobId: JobId? = null,
    ) : DeepLinkDestinations(
        value = "build-logs",
        navArgs = mapOf(
            "accountId" to accountId.getValue().toString(),
            "projectId" to projectId.getValue(),
            "buildId" to buildId.getValue(),
            "jobId" to jobId?.getValue(),
        ),
    )

    data class JobsList(
        private val accountId: AccountId,
        private val projectId: ProjectId,
        private val buildId: BuildId,
        private val title: String?,
    ) : DeepLinkDestinations(
        value = "job-list",
        navArgs = mapOf(
            "accountId" to accountId.getValue().toString(),
            "projectId" to projectId.getValue(),
            "buildId" to buildId.getValue(),
            "title" to title,
        ),
    )
}
