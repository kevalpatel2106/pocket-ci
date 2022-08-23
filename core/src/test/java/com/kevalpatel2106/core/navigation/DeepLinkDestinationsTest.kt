package com.kevalpatel2106.core.navigation

import com.kevalpatel2106.core.navigation.DeepLinkDestinations.AccountList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.ArtifactList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.BuildList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.BuildLog
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.CiSelection
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.JobList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.ProjectList
import com.kevalpatel2106.core.navigation.DeepLinkDestinations.SettingList
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import com.kevalpatel2106.entity.id.ProjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

@Suppress("UnusedPrivateMember")
internal class DeepLinkDestinationsTest {

    @ParameterizedTest(name = "check {0} destination value is {1}")
    @MethodSource("provideValues")
    fun `check destination value`(
        destinations: DeepLinkDestinations,
        value: String,
        navArgs: Map<String, String?>,
    ) {
        val actual = destinations.value
        assertEquals(value, actual)
    }

    @ParameterizedTest(name = "check {0} destination nav args is {2}")
    @MethodSource("provideValues")
    fun `check settings list destination nav args`(
        destination: DeepLinkDestinations,
        value: String,
        navArgs: Map<String, String?>,
    ) {
        val actual = destination.navArgs
        assertEquals(navArgs, actual)
    }

    companion object {
        @Suppress("unused", "LongMethod")
        @JvmStatic
        fun provideValues() = listOf(
            arguments(SettingList, "setting-list", emptyMap<String, String?>()),
            arguments(CiSelection, "ci-selection", emptyMap<String, String?>()),
            arguments(AccountList, "account-list", emptyMap<String, String?>()),
            arguments(
                ProjectList(AccountId(1)),
                "project-list",
                mapOf("accountId" to 1.toString()),
            ),
            arguments(
                BuildList(AccountId(1), ProjectId("test-project")),
                "build-list",
                mapOf("accountId" to 1.toString(), "projectId" to "test-project"),
            ),

            // region DeepLinkDestinations.BuildLog
            arguments(
                BuildLog(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                ),
                "build-logs",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "jobId" to null,
                ),
            ),
            arguments(
                BuildLog(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                    JobId("test-job"),
                ),
                "build-logs",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "jobId" to "test-job",
                ),
            ),
            // endregion

            // region DeepLinkDestinations.ArtifactList
            arguments(
                ArtifactList(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                    null,
                ),
                "artifact-list",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "title" to null,
                ),
            ),
            arguments(
                ArtifactList(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                    "test-title",
                ),
                "artifact-list",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "title" to "test-title",
                ),
            ),
            // endregion

            // region DeepLinkDestinations.JobList
            arguments(
                JobList(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                    null,
                ),
                "job-list",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "title" to null,
                ),
            ),
            arguments(
                JobList(
                    AccountId(1),
                    ProjectId("test-project"),
                    BuildId("test-build"),
                    "test-title",
                ),
                "job-list",
                mapOf(
                    "accountId" to 1.toString(),
                    "projectId" to "test-project",
                    "buildId" to "test-build",
                    "title" to "test-title",
                ),
            ),
            // endregion
        )
    }
}
