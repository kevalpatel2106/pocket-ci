package com.kevalpatel2106.core.navigation

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId

sealed class DeepLinkDestinations(val value: String, val navArgs: Map<String, String?>) {
    object SettingList : DeepLinkDestinations("setting-list", emptyMap())
    object CiSelection : DeepLinkDestinations("ci-selection", emptyMap())
    object AccountList : DeepLinkDestinations("account-list", emptyMap())

    data class ProjectList(private val accountId: AccountId) : DeepLinkDestinations(
        value = "project-list",
        navArgs = mapOf("accountId" to accountId.getValue().toString()),
    )

    data class BuildList(
        private val accountId: AccountId,
        private val projectId: ProjectId,
    ) : DeepLinkDestinations(
        value = "build-list",
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

    data class ArtifactList(
        private val accountId: AccountId,
        private val projectId: ProjectId,
        private val buildId: BuildId,
        private val title: String?,
    ) : DeepLinkDestinations(
        value = "artifact-list",
        navArgs = mapOf(
            "accountId" to accountId.getValue().toString(),
            "projectId" to projectId.getValue(),
            "buildId" to buildId.getValue(),
            "title" to title,
        ),
    )

    data class JobList(
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
