package com.kevalpatel2106.core.navigation

import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.ProjectId

sealed class DeepLinkDestinations(val value: String, val navArgs: List<String>) {
    object Settings : DeepLinkDestinations("settings", emptyList())
    object CiSelection : DeepLinkDestinations("ci-selection", emptyList())
    object AccountsList : DeepLinkDestinations("accounts-list", emptyList())

    data class ProjectsList(private val accountId: AccountId) : DeepLinkDestinations(
        value = "projects",
        navArgs = listOf(accountId.getValue().toString()),
    )

    data class BuildsList(
        private val accountId: AccountId,
        private val projectId: ProjectId,
    ) : DeepLinkDestinations(
        value = "builds-list",
        navArgs = listOf(accountId.getValue().toString(), projectId.getValue()),
    )
}
